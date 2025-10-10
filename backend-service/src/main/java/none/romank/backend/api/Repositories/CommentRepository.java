package none.romank.backend.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import none.romank.backend.api.Domain.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment,Long>{

    @Query(value="SELECT c FROM Comment c WHERE c.article.id =:articleId")
    public List<Comment> getCommentsForArticleById(@Param("articleId") Long articleId);

    //here because I added in the @Entity classes the @ManyToOne and @OneToMany annotations to create relationships
    //between Comment <--- [n to 1] ---> Article, Comment <--- [n to 1] ---> User, we don't need a method like :
    //@Query(value="select (article_id) from Comment where id =: cid",nativeQuery=true), agav, need to learn JPQL for poratbility
    //public long getArticleIdForComment(@Param("cid") Long cid)
    //because Hibernate, when uses Proxies for the 'article' property, and because it is marked with fetch-type: lazy,
    // the proxy object does have a ArticleProxy (lo 1 to 1 name) with it's 'id' field instantiated to what sits at
    // the 'article_id' column of the corresponding Article.
    // So basically, instead of impleementing the above mentioned getArticleIdForComment(...) use:
    // ++++++++ Comment comment = CommentService.findCommentById(commentId);
    // ++++++++ Long owningArticleId = comment.getArticle().getId(); <--- doesn't run Queries on the database because
    // getArticle is a proxy, and we are LAZY loading.
    
}
