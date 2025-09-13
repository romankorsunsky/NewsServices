package none.romank.backend.api;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import none.romank.backend.Article;
import none.romank.backend.ArticleRepository;

@RestController
@CrossOrigin(origins={"localhost://8080"})
@RequestMapping(path="/api/articles",produces="application/json")
public class ArticlesInfoController {

    private final ArticleRepository artRep;

    @Autowired
    public ArticlesInfoController(ArticleRepository repo){
        this.artRep = repo;
    }

    @GetMapping(params="bydatelatest")
    public List<Article> getArticlesByDateLatest() {
        BufferedWriter wr; 
        wr = new BufferedWriter(new OutputStreamWriter(System.out));
        try {
            wr.write("API/LATEST REQUESTED!"); 
            wr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }      
        return artRep.findArticlesSortedByDateLatest();
    }
    @GetMapping
    public ResponseEntity<Article> getArticlesByDateOldest(@RequestParam("id") Long id) {
        Optional<Article> article = artRep.findById(id);
        List<Article> artRet = new ArrayList<>();
        if(article.isPresent()){
            return new ResponseEntity<>(article.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    
}
