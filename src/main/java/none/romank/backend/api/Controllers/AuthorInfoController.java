package none.romank.backend.api.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;

import none.romank.backend.api.Services.UserService;




@RestController
@RequestMapping(path="api/authors",produces="application/json")
@CrossOrigin(origins={"http://localhost:8080"})
public class AuthorInfoController {

    //private final UserDetailsRepository userRepo;
    private final UserService userService;
    
    @Autowired
    public AuthorInfoController(/*UserDetailsRepository repo,*/UserService userService){
        this.userService = userService;
        //this.userRepo = repo;
    }

    @GetMapping(params="authors")
    public List<String> getAuthors() {
        return userService.findAuthorsNames();
    }
}
