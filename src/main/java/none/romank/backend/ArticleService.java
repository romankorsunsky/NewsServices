package none.romank.backend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class ArticleService {

    private final ArticleRepository artRep;

    @Autowired
    public ArticleService(ArticleRepository artrep){
        this.artRep = artrep;
        
    }

    public List<Article> getFeedArticles(){
        List<Article> res = new ArrayList<>();
        for (Article a : artRep.findTopXByViews(5)) {
            res.add(a);   
        }
        return res;
    }
}
