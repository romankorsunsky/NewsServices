package none.romank.backend.api.Domain;


import java.time.LocalDate;

import lombok.Data;
@Data
public class ArticleDTO{
    private LocalDate dateOfPublish;
    
    private Long authorId;
    
    private String title;
    
    private String content;
    
    private String taglist;

    private String imagePath;
    
    private Long views;

    public ArticleDTO(Long authorId,LocalDate dateOfPublish,String title,String content,String taglist,String imagePath,Long views){
        this.authorId = authorId;
        this.dateOfPublish = dateOfPublish;
        this.title = title;
        this.content = content;
        this.taglist = taglist;
        this.imagePath = imagePath;
        this.views = views;
    }
}
