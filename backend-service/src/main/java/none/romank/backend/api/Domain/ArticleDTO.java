package none.romank.backend.api.Domain;


import java.time.LocalDate;

import lombok.Data;
@Data
public class ArticleDTO{
    
    private Long id;

    private LocalDate createdAt;
    private Long authorId;
    private String title;
    private String content;
    private String taglist;
    private String category;
    private String imagePath;
    private Long views;

    public ArticleDTO(Long id,Long authorId,LocalDate createdAt,String title,String content,String taglist,String imagePath,Long views,String category){
        this.id = id;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.title = title;
        this.content = content;
        this.taglist = taglist;
        this.imagePath = imagePath;
        this.views = views;
        this.category = category;
    }
}
