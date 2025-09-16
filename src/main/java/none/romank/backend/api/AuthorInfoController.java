package none.romank.backend.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import none.romank.backend.AuthorityGrantRequest;
import none.romank.backend.UserService;



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
    //for now this method is a place holder, it has incorrect parameter types and much more errors, IGNORE it.
    @PostMapping(path="requestauth",consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorityGrantRequest postNewAuthorAuthorityGrantRequest(@RequestBody String entity) {
        
        return null;
    }
    
}
