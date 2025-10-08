package  none.romank.backend.api.Domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Article{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id; 

    @Column(name="created_at")
    private LocalDate createdAt;
    
    @Column(name="title")
    private String title;
    
    @Column(name="content")
    private String content;
    
    @Column(name="tags")
    private String taglist;

    @Column(name="imguri")
    private String imagePath;
    
    @Column(name="views")
    private Long views = 0L;
    
    @Enumerated(EnumType.STRING)
    @Column(name="category")
    private Category category; //Spring Data, when seeing an Enum, will just translate it to varchar

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="author_id")
    private Author author;

    @OneToMany(mappedBy="article",fetch=FetchType.LAZY)
    private Set<Comment> comments;

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

    public static ArticleDTO toDTO(Article article){
        return new ArticleDTO(
            article.id,
            article.getAuthor().getId(),
            article.createdAt,
            article.title,
            article.content,
            article.imagePath,
            article.taglist,
            article.views,
            article.category.name());
    }
}