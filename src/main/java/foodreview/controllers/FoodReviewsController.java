package foodreview.controllers;
import foodreview.Repository.FoodReviewsRepository;
import foodreview.Utils;
import foodreview.Values;
import foodreview.models.Review;
import foodreview.models.ReviewFilter;
import foodreview.models.UserReviews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import javax.persistence.EntityManager;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/food-reviews")
public class FoodReviewsController {

    @Autowired
    FoodReviewsRepository foodReviewsRepository;

    @Autowired
    EntityManager em = Utils.getEntityManager();

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllFoodReviews(
            @RequestParam(defaultValue = Values.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Values.DEFAULT_NAME_PER_PAGE) int numPerPage) {
        try {
            Pageable paging = PageRequest.of(page, numPerPage);
            Page<Review> pageSummary = foodReviewsRepository.findAll(paging);
            List<Review> reviews = pageSummary.getContent();

            Map<String, Object> response = Utils.getReviewsResponse(reviews,pageSummary);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<Map<String, Object>> searchFoodReviews(
            @RequestBody List<ReviewFilter> filters,
            @RequestParam(defaultValue =  Values.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Values.DEFAULT_NAME_PER_PAGE) int numPerPage){

        try {
            Pageable paging = PageRequest.of(page, numPerPage);
            Page<Review> pageSummary;
            //get filter values
            ReviewFilter score=Utils.getFilterOrNull(filters,"score");
            ReviewFilter productId=Utils.getFilterOrNull(filters,"productId");
            if(score!=null && productId!=null){
                pageSummary = foodReviewsRepository.findByProductIdAndScore(productId.getValue(),
                        Integer.parseInt(score.getValue()), paging);
            }else if(score!=null){
                pageSummary = foodReviewsRepository.findByScore(Integer.parseInt(score.getValue()), paging);
            } else {
                pageSummary = foodReviewsRepository.findByProductId(productId.getValue(), paging);
            }
            List<Review> reviews = pageSummary.getContent();

            Map<String, Object> response = Utils.getReviewsResponse(reviews,pageSummary);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/top-reviewers")
    public ResponseEntity<Map<String, Object>> searchTopReviewers(){
        try {
            Collection<UserReviews> data  = foodReviewsRepository.findTopReviewers();

            Map<String, Object> response = new HashMap<>();
            response.put("users", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Version 2 of the search using entity manager.
    // In this version the "where" clause is creating dynamic and could be extend to
    // un-limit number of filter fields without any changes in the api.
    @PostMapping("/search-unlimit")
    public ResponseEntity<Map<String, Object>> searchAllFiltersSearch(
            @RequestBody List<ReviewFilter> filters,
            @RequestParam(defaultValue =  Values.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Values.DEFAULT_NAME_PER_PAGE) int numPerPage){

        try {
            int offset= Utils.getResultsOffset(page,numPerPage);
            String where = Utils.getWhereCondition(filters);
            String query = String.format("select Id, ProductId, UserId, ProfileName, HelpfulnessNumerator, HelpfulnessDenominator, Score, Time, Summary, Text from review where %s LIMIT %s OFFSET %s",where, numPerPage , offset);
            List<Object[]> rows =em.createNativeQuery(query).getResultList();
            List<Review> result = new ArrayList<>(rows.size());
            for (Object[] r : rows) {
                Review a=new Review((String) r[0],(String)r[1],(String)r[2],(String)r[3]
                        ,(Integer) r[4],(Integer)r[5],(Integer)r[6],
                        (String)r[7],(String)r[8],(String)r[9]);
                result.add(a);
            }
            String countQuery="select count(*) from review";

            int totalItems = ((Number) em.createNativeQuery(countQuery).getSingleResult()).intValue();
            Map<String, Object> response = new HashMap<>();
            double totalPages=Math.ceil((double)totalItems/(double)numPerPage);
            response.put("reviews", result);
            response.put("currentPage", page);
            response.put("totalItems", totalItems);
            response.put("totalPages", totalPages);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

