package none.romank.backend.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import none.romank.backend.api.Repositories.CommentRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepo;

    @Autowired
    public CommentService(CommentRepository commentRepo){
        this.commentRepo = commentRepo;
    }

    
}
