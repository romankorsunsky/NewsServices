package none.romank.backend.api.Domain;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private LocalDate createdAt;
    private String content;
    
    private long commentor_id;
    private long article_id;

    public CommentDTO(Comment comment){
        this.id = comment.getId();
        this.createdAt = comment.getCreatedAt();
        this.content = comment.getContent();
        this.commentor_id = comment.getCommentor().getId();
        this.article_id = comment.getArticle().getId();
    }
}
