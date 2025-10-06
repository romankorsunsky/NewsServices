package none.romank.backend.api.Controllers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import none.romank.backend.api.Domain.Article;
import none.romank.backend.api.Services.ArticleService;


@RestController
@CrossOrigin(origins={"localhost:8080"})
@RequestMapping(path="/api/articles",produces="application/json")
public class ArticlesInfoController {

    private final ArticleService serv;

    @Autowired
    public ArticlesInfoController(ArticleService serv){
        this.serv = serv;
    }

    @GetMapping(path="/bydatelatest")
    public List<Article> getArticlesByDateLatest() {
        BufferedWriter wr; 
        wr = new BufferedWriter(new OutputStreamWriter(System.out));
        try {
            wr.write("API/LATEST REQUESTED!"); 
            wr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }      
        return serv.findArticlesSortedByDateLatest();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticlesById(@PathVariable("id") Long id) {
        Optional<Article> article = serv.findArticleById(id);
        System.out.println("========================GETTING ARTICLE=====================");
        if(article.isPresent()){
            return new ResponseEntity<>(article.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    /*curl -v -X POST -H "Content-Type: application/json" -d '{"authorId":1,"title":"POSTArticle2","taglist":"1,2,3","category":"FINANCE","content":"behold the content2"}' http://localhost:8080/api/articles/addarticle */
    @PostMapping(path="addarticle",consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
        ResponseEntity<Article> response;
        article.setDateOfPublish(LocalDate.now());
        article.sortTags();
        article.setImagePath("/images/default.png");
        System.out.println("========================POSTING ARTICLE=====================");
        try{
            Article articleSaved = serv.saveArticle(article);
            return new ResponseEntity<>(articleSaved, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Article> deleteArticleById(@PathVariable Long id){
        Optional<Article> deletedArticle = serv.findArticleById(id);
        if(deletedArticle.isPresent()){
            serv.deleteArticleById(id);
            return new ResponseEntity<>(deletedArticle.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
