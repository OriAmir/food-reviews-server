package foodreview.models;
import javax.persistence.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
@Table(name = "Review")
public class Review implements Serializable {

    @Id
    private String id;

    @Column(name = "productid")
    private String productId;

    @Column(name = "userid")
    private String userId;

    @Column(name = "profilename")
    private String profileName;

    @Column(name = "helpfulnessnumerator")
    private Integer helpfulnessNumerator;

    @Column(name = "helpfulnessdenominator")
    private Integer helpfulnessDenominator;

    @Column(name = "score")
    private Integer score;

    @Column(name = "time")
    private String time;

    @Column(name = "summary",columnDefinition="varchar(1000000)")
    private String summary;

    @Column(name = "text",columnDefinition="varchar(1000000)")
    private String text;

    public Review(){}

    public Review(String id, String productId,String userId,String profileName,
                  Integer helpfulnessNumerator,Integer helpfulnessDenominator,Integer score,
                  String time,String summary,String text
                  ) {
        this.id=id;
        this.productId = productId;
        this.userId = userId;
        this.profileName =profileName;
        this.helpfulnessNumerator=helpfulnessNumerator;
        this.helpfulnessDenominator=helpfulnessDenominator;
        this.score=score;
        this.time=time;
        this.summary=summary;
        this.text=text;
    }
}
