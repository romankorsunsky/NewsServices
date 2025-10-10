package none.romank.backend.api.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import none.romank.backend.api.Domain.Article;
import none.romank.backend.api.Domain.ArticleDTO;
import none.romank.backend.api.Repositories.ArticleRepository;

@Service
@Component
public class ArticleService {
    private static final int MAX_CONTENT_LENGTH = 256;
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
        return artRep.findArticlesSortedByDateLatestLimitX(10L).stream(). //change 10L later to mean somwthing
            map(article -> Article.toDTO(article)).collect(toList());
    }

    public void deleteArticleById(Long id) {
        artRep.deleteById(id);
    }

    @Transactional
    public Optional<Object> changeArticleContent(Long id,Article artWithContent) {
        Optional<Article> artCont = artRep.findById(id);
        if(artCont.isPresent()){
            Article article = artCont.get();
            String content = artWithContent.getContent();
            if(content != null && content.length() > 0 && content.length() <= MAX_CONTENT_LENGTH){
                //I mean, reallistically I should add editorial_status to each Article entity to approve
                //the content, or at least filter some bad words, I don't know. 
                article.setContent(content);
                artRep.save(article);
                return Optional.of(true);
            }
            return Optional.empty();
        }
        return Optional.empty();
    }
}
