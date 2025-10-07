package none.romank.backend.api.Domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private LocalDate createdAt;

    private String content;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="commentor_id")
    private User commentor; //nirali I need commentator ve lo commentor aval lo meshane

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="article_id")
    private Article article;

    public Long getId(){
        return this.id;
    }
}
