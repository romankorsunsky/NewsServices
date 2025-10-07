package none.romank.backend.api.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import none.romank.backend.api.Domain.Article;
import none.romank.backend.api.Domain.ArticleDTO;
import none.romank.backend.api.Repositories.ArticleRepository;

@Service
@Component
public class ArticleService {

    private final ArticleRepository artRep;

    @Autowired
    public ArticleService(ArticleRepository artrep){
        this.artRep = artrep;
        
    }

    public ArticleDTO saveArticle(Article article) throws Exception{
        Article a;
        try {
            a = artRep.save(article);
            return Article.toDTO(a);
        } 
        //check that such an Author exists.
        catch (OptimisticLockingFailureException e) {
            throw new Exception("Failed to create try again");
        }
        catch (IllegalArgumentException e) {
            throw new Exception("Bad Article");
        }

    }

    public List<ArticleDTO> getFeedArticles(){
        List<Article> articles = artRep.findTopXByViews(5);
        List<ArticleDTO> result = new ArrayList<>(0);
        for (Article a : articles) {
            result.add(Article.toDTO(a));   
        }
        return result;
    }

    public Optional<ArticleDTO> findArticleById(Long id){
        Optional<Article> artContainer = artRep.findById(id);
        Optional<ArticleDTO> result = Optional.ofNullable(Article.toDTO(artContainer.get()));
        return result;
    }

    public List<ArticleDTO> findArticlesSortedByDateLatest(){
        return artRep.findArticlesSortedByDateLatest().stream().
            map(article -> Article.toDTO(article)).collect(toList());
    }

    public void deleteArticleById(Long id) {
        artRep.deleteById(id);
    }
}
