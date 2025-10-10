package none.romank.backend.api.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import none.romank.backend.api.Domain.Author;
import none.romank.backend.api.Domain.AuthorDTO;
import none.romank.backend.api.Services.AuthorService;


@RestController
@RequestMapping(path="api/authors",produces="application/json")
@CrossOrigin(origins={"http://localhost:8080"})
public class AuthorInfoController {

    private final AuthorService authorService;

    
    @Autowired
    public AuthorInfoController(AuthorService authorService){
        this.authorService = authorService;
    }
    @PostMapping("/sync")
    public ResponseEntity<AuthorDTO> postAuthor(@RequestBody Author author) {
        return ResponseEntity.of(authorService.saveAuthor(author));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorInfo(@PathVariable("id") Long id) {
        Optional<AuthorDTO> authdtoContainer = authorService.findAuthorById(id);
        if(authdtoContainer.isPresent()){    
            AuthorDTO adto = authdtoContainer.get();
            return new ResponseEntity<>(adto,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(params="authors")
    public List<String> getAuthorNames() {
        return authorService.findAllAuthorNames();
    }
}
