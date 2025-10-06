package none.romank.backend.api.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;

import none.romank.backend.api.Domain.Author;
import none.romank.backend.api.Domain.AuthorDTO;
import none.romank.backend.api.Domain.User;
import none.romank.backend.api.Services.AuthorService;





@RestController
@RequestMapping(path="api/authors",produces="application/json")
@CrossOrigin(origins={"http://localhost:8080"})
public class AuthorInfoController {

    //private final UserDetailsRepository userRepo;
    private final AuthorService authorService;

    
    @Autowired
    public AuthorInfoController(AuthorService authorService){
        this.authorService = authorService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorInfo(@PathVariable("id") Long id) {
        Optional<Author> authContainer = authorService.findAuthorById(id);
        if(authContainer.isPresent()){
            Author author = authContainer.get();
            User user = author.getUser();
            AuthorDTO adto = new AuthorDTO(user,author.getPfpUri(), author.getBio());
            return new ResponseEntity<>(adto,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(params="authors")
    public List<String> getAuthors() {
        return authorService.findAllAuthorNames();
    }
}
