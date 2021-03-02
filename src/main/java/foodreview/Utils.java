package foodreview;

import foodreview.models.Review;
import foodreview.models.ReviewFilter;
import org.springframework.data.domain.Page;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Utils {

    public static EntityManager getEntityManager() {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("food_review");
        EntityManager em=emf.createEntityManager();
        return em;
    }

    //create response object to the client with the results
    public static Map<String, Object> getReviewsResponse (
            List<Review> reviews, Page<Review> summary){
        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviews);
        response.put("currentPage", summary.getNumber());
        response.put("totalItems", summary.getTotalElements());
        response.put("totalPages", summary.getTotalPages());
        return response;
    }

    //create the where clause with all the filters
    public static String getWhereCondition (List<ReviewFilter> filters){

        String where="";
        for (Iterator<ReviewFilter> r = filters.iterator(); r.hasNext();) {
            ReviewFilter item = r.next();
            where+= String.format("%s='%s' ",item.getId(),item.getValue());
            if(r.hasNext()) {
                where += "and ";
            }
        }
        return where;
    }

    //check if filter field is inside filters list
    public static ReviewFilter getFilterOrNull (List<ReviewFilter> filters,String field){
        ReviewFilter filter= filters.stream().filter(f -> f.getId().contains(field)).findFirst().orElse(null);
        return filter;
    }

    //calc page offset
    public static int getResultsOffset(int pageNum,int numPerPage) {
        int offset = 0;
        if(pageNum>= 1) {
            offset = (pageNum) * (numPerPage);
        }
        return offset;
    }
}