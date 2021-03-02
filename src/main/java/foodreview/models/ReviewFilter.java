package foodreview.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ReviewFilter {

    private String Id;
    private String value;

    public ReviewFilter(String id, String value){
        this.Id= id;
        this.value= value;
    }

    public String getId(){
        return this.Id;
    }

    public String getValue(){
        return this.value;
    }
}
