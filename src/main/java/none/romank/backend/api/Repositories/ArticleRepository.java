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

    @Query(value = "select * from article where author_id = :id",nativeQuery=true)
    public List<Article> findByAuthorId(@Param("id") Long id);

    @Query(value = "select * from article where title = :title",nativeQuery=true)
    public Optional<Article> findByTitle(@Param("title") String title);

    @Query(value = "update article set status = :status where id = :id",nativeQuery=true)
    @Modifying //test if I need to remove it later, but it makes sense to add it because we dont retreive the Article object later
    public int updateStatusById(@Param("id") Long id,@Param("status") String status);

    @Query(value="update article set status = :newStatus where title =:title",nativeQuery=true)
    @Modifying
    public int updateStatusByTitle(@Param("title") String title,@Param("newStatus") String newStatus);

    @Query(value="select * from article order by created_at desc",nativeQuery=true)
    public List<Article> findArticlesSortedByDateLatest();
}
