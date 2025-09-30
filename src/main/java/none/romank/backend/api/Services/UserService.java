package none.romank.backend.api.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import none.romank.backend.api.Domain.User;
import none.romank.backend.api.Repositories.UserDetailsRepository;

@Service
public class UserService {

    private final UserDetailsRepository userRepo;

    @Autowired
    public UserService(UserDetailsRepository repo){
        this.userRepo = repo;
    }

    public List<String> findAuthorsNames(){
        List<User> users = userRepo.findAuthors();
        List<String> authorsNames = users.stream().filter(
            user -> {return user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_AUTHOR"));}
        ).map(usr -> usr.getUsername()).collect(Collectors.toList());
        return authorsNames;
    }
}
