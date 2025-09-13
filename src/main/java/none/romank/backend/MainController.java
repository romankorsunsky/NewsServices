package none.romank.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;



@Controller
@SessionAttributes("articlesList")
public class MainController {
    private final ArticleService articleService;
    
    @Autowired
    public MainController(ArticleService articleService){
        this.articleService = articleService;
    }
    @GetMapping("/")
    public String getHome(Model model) {
        List<Article> articles = articleService.getFeedArticles();
        model.addAttribute("articlesList", articles);
        return "home";
    }
    
}
