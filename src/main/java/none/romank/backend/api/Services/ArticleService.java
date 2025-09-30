package none.romank.backend.api.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import none.romank.backend.ArticleCreateFailException;
import none.romank.backend.api.Domain.Article;
import none.romank.backend.api.Repositories.ArticleRepository;

@Service
@Component
public class ArticleService {

    private final ArticleRepository artRep;

    @Autowired
    public ArticleService(ArticleRepository artrep){
        this.artRep = artrep;
        
    }

    public Article saveArticle(Article article){
        Article a;
        try {
            a = artRep.save(article);
            return a;
        } 
        catch (OptimisticLockingFailureException e) {
            throw new ArticleCreateFailException("Failed to create try again");
        }
        catch (IllegalArgumentException e) {
            throw new ArticleCreateFailException("Bad Article");
        }

    }

    public List<Article> getFeedArticles(){
        List<Article> res = new ArrayList<>();
        for (Article a : artRep.findTopXByViews(5)) {
            res.add(a);   
        }
        return res;
    }

    public Optional<Article> findArticleById(Long id){
        return artRep.findById(id);
    }

    public List<Article> findArticlesSortedByDateLatest(){
        List<Article> articles;
        articles = artRep.findArticlesSortedByDateLatest();
        return articles;
    }

    public void deleteArticleById(Long id) {
        artRep.deleteById(id);
    }
}
