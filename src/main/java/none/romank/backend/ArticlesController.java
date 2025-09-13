package none.romank.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/articles")
public class ArticlesController {
    private final ArticleRepository dbservice;

    @Autowired
    public ArticlesController(ArticleRepository ds){
        this.dbservice = ds;
    }
    @GetMapping("")
    public String getArticles() {
        log.info("Pulling articles from some dataSource");
        return "articles";
    }

}
