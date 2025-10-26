package none.romank.backend.api.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import none.romank.backend.api.Domain.Comment;
import none.romank.backend.api.Domain.CommentDTO;
import none.romank.backend.api.Services.CommentService;



@RestController
@RequestMapping("api/comments")
public class CommentController {
    
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commServ){
        this.commentService = commServ;
    }
    /*
     * Returns a list of comments for a specific article,
     * RestClient is smart enough to creatve an empty HTTP response body if the Optional<> argument
     * in it's creation return false on opt.isPresent()
     */
    @GetMapping("/forarticle/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsForArticle(@PathVariable Long articleId) {
        List<CommentDTO> comments;
        comments = commentService.getCommentsByArticleId(articleId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    /*
     * add a comment, it is the sender's responsibility to provide an user id and article id
     */
    @PostMapping("/addcomment")
    public ResponseEntity<CommentDTO> addCommentForArticle(@RequestBody Comment cmnt) {
        CommentDTO comm = commentService.addComment(cmnt).get();
        System.out.println("ADDING COMMENT BRUV");
        return new ResponseEntity<>(comm,HttpStatus.OK);
    }
    
}
