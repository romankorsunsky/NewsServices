package none.romank.backend.api.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import none.romank.backend.api.Domain.Author;
import none.romank.backend.api.Domain.AuthorDTO;
import none.romank.backend.api.Domain.User;
import none.romank.backend.api.Repositories.AuthorRepository;
import none.romank.backend.api.Repositories.UserRepository;

@Service
public class AuthorService {
    private final AuthorRepository authorRepo;
    private final UserRepository userRepo;

    @Autowired

    public AuthorService(AuthorRepository authorRepo,UserRepository userRepo){
        this.authorRepo = authorRepo;
        this.userRepo = userRepo;
    }

    public Optional<AuthorDTO> findAuthorById(Long id){
        Optional<Author> auth = authorRepo.findById(id);
        return Optional.ofNullable((Author.toDTO(auth.get()))); //this is stupid by me actually,
                                        // gotta think if can be modified by a different request
        
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

    @Transactional
    public Optional<AuthorDTO> saveAuthor(Author author){
        //return a DTO to the controller, the conversion is buisiness logic do it here
        Long userId = author.getId(); //get the Id
        Optional<User> userOfAuthor = userRepo.findUserById(userId); //find the User with the Id
        if(userOfAuthor.isPresent()){   //there is such a User
            Author newAuth = new Author();
            newAuth.setBio(author.getBio());
            newAuth.setPfpUri(author.getPfpUri());
            newAuth.setUser(userOfAuthor.get()); //isPresent() shave true so we can do get().
            //also, regarding the ^ line, DO NOT use newAuth.setId(haIdShelUser) because when we set
            // the user property of Author objects, Spring JPA sees that we have a
            authorRepo.save(newAuth);
            return Optional.of(Author.toDTO(newAuth));
        }
        else{
            return Optional.empty();
        }
    }
}
