package foodreview.Repository;

import foodreview.models.Review;
import foodreview.models.UserReviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Collection;

public interface FoodReviewsRepository extends JpaRepository<Review, Long> {

     Page<Review> findAll(Pageable pageable);
     Page<Review> findByProductId(String productId,Pageable pageable);
     Page<Review> findByScore(Integer Score,Pageable pageable);
     Page<Review> findByProductIdAndScore(String productId, Integer Score,Pageable pageable);
     @Query(value = "SELECT USERID as userId ,COUNT(*) as totalReviews FROM REVIEW" +
                    " group by userId order by totalReviews desc limit 10" , nativeQuery = true)
     Collection<UserReviews> findTopReviewers();

}