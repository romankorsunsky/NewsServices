package none.romank.backend.api.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import none.romank.backend.api.Domain.Article;

public interface ArticleRepository extends CrudRepository<Article,Long>{
    //remember to change all the queries here from PSGRS native to JPQL which is portable (need to check performnce tradeoffs though)
    @Query(value = "select * from article order by views desc limit :limit",nativeQuery=true)
    public List<Article> findTopXByViews(@Param("limit") Integer limit);

    @Query(value = "SELECT a FROM Article a WHERE a.author.id =:id")
    public List<Article> findByAuthorId(@Param("id") Long id);

    @Query(value = "SELECT a FROM Article a WHERE title =:title") //don't forget to make sure that title is UNIQUE in the DB
    public Optional<Article> findByTitle(@Param("title") String title);

    @Query(value="UPDATE Article a SET a.content =:content WHERE a.id =:id")
    @Modifying
    public int updateStatusByTitle(@Param("id") Long id,@Param("content") String content);

    @Query(value="SELECT a FROM Article a ORDER BY a.createdAt DESC LIMIT :limit")
    public List<Article> findArticlesSortedByDateLatestLimitX(@Param("limit") Long limit);

}
