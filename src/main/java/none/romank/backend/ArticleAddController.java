package none.romank.backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Controller
@RequestMapping("/addarticle")

public class ArticleAddController {
    private final ArticleRepository artrep;

    @Autowired
    public ArticleAddController(ArticleRepository artrep){
        this.artrep = artrep;

    }

    @GetMapping("")
    public String addArticlePage(Model model){
        List<String> catvals = new ArrayList<>();
        for (Article.Category cat : Article.Category.values()) {
            catvals.add(cat.toString());
        }
        model.addAttribute("categoryValues", catvals);
        model.addAttribute("newArticle", new Article());
        return "addarticle";
    }
    @PostMapping("")
    public String addArticleStep1(@ModelAttribute("newArticle") Article article,Model model) {
        log.info("USER ADDING ARTICLE");
        article.setDateOfPublish(LocalDate.now());
        article.setViews(0L);
        article.sortTags();
        try{
            article.setImagePath("/images/default.png");
            artrep.save(article);
            log.info((article.toString()));
            return "redirect:/";
        }
        catch(BadArticleArgumentsException e){
            log.info(e.getMessage());
            return "redirect:/";
        }
    }
    
}
