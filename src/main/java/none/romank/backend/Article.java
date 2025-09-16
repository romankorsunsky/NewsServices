package  none.romank.backend;

import java.time.LocalDate;
import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Article{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id; 

    @Column(name="date_of_pub")
    private LocalDate dateOfPublish;
    
    @Column(name="author_id")
    private Long authorId;
    
    @Column(name="title")
    private String title;
    
    @Column(name="content")
    private String content;
    
    @Column(name="tags")
    private String taglist;
    
    @Column(name="image_path")
    private String imagePath;
    
    @Column(name="views")
    private Long views;
    
    @Enumerated(EnumType.STRING)
    @Column(name="category")
    private Category category; //Spring Data, when seeing an Enum, will just translate it to varchar
    
    @Column(name="status")
    private String status = "PENDING";

    public Article(){
        
    }
    //this method sorts the comma separated tags, just in case we will need to retrieve them later
    //so they will already be kept in order to speed up tag matching.
    public void sortTags(){
        String buf = this.taglist;
        String[] separated = buf.split(",");
        Arrays.sort(separated,(s1,s2)->
            {return s1.compareTo(s2);});
        this.taglist = String.join(",", separated);
        System.out.println(this.taglist);
    }

    public enum Category {
        MILITARY, ISRAEL, US, FINANCE, UKRAINE, OPINION, OTHER
    }
}