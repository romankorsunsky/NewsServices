package none.romank.backend.api.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import none.romank.backend.api.Domain.Author;
import none.romank.backend.api.Repositories.AuthorRepository;

@Service
public class AuthorService {
    private final AuthorRepository authorRepo;

    @Autowired
    public AuthorService(AuthorRepository authorRepo){
        this.authorRepo = authorRepo;
    }
    public Optional<Author> findAuthorById(Long id){
         Optional<Author> auth = authorRepo.findById(id);
         if(auth.isPresent()){
            return Optional.of(auth.get()); //this is stupid by me actually,
                                            // gotta think if can be modified by a different request
         }
         return auth;
    }

    public List<String> findAllAuthorNames(){
        List<Author> authors = authorRepo.findAllAuthors();
        List<String> names = new ArrayList<>(0);
        if(!authors.isEmpty()){
            for (Author author : authors) {
                
                names.add(author.getUser().getFirstName().concat(" " + author.getUser().getLastName()));
            }
        }
        return names;
    }
}
