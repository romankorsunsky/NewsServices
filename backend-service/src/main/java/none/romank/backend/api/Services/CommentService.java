package none.romank.backend.api.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import none.romank.backend.api.Domain.Article;
import none.romank.backend.api.Domain.Comment;
import none.romank.backend.api.Domain.CommentDTO;
import none.romank.backend.api.Domain.User;
import none.romank.backend.api.Repositories.ArticleRepository;
import none.romank.backend.api.Repositories.CommentRepository;
import none.romank.backend.api.Repositories.UserRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepo;
    private final ArticleRepository articleRepo;
    private final UserRepository userRepo;

    @Autowired
    public CommentService(CommentRepository commentRepo,ArticleRepository articleRepo,UserRepository userRepo){
        this.commentRepo = commentRepo;
        this.articleRepo = articleRepo;
        this.userRepo = userRepo;
    }

    public List<CommentDTO> getCommentsByArticleId(Long articleId) {
        Optional<Article> article = articleRepo.findById(articleId);
        List<CommentDTO> comments;
        if(article.isPresent()){
             comments = article.get().getComments().
                stream().map(c -> Comment.toDTO(c)).collect(Collectors.toList());
            
                return comments;
        }
        return new ArrayList<CommentDTO>(0);
    }

    @Transactional
    public Optional<CommentDTO> addComment(Comment comment){
        Optional<User> user = userRepo.findById(comment.getCommentor().getId());
        Optional<Article> article = articleRepo.findById(comment.getArticle().getId());
        if(!user.isPresent() || !article.isPresent()){ //if the User or the article don't exist return empty Optional
            return Optional.empty();
        }
        Comment newComm = new Comment();
        newComm.setArticle(article.get());
        newComm.setCommentor(user.get());
        newComm.setCreatedAt(LocalDate.now());
        if(comment.getContent().isEmpty()){
            return Optional.empty();
        }
        newComm.setContent(comment.getContent());
        commentRepo.save(newComm);
        return Optional.of(Comment.toDTO(newComm));
    }
}
