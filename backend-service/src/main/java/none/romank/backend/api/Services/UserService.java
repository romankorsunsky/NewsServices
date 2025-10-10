package none.romank.backend.api.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import none.romank.backend.api.Domain.User;
import none.romank.backend.api.Domain.UserDTO;
import none.romank.backend.api.Repositories.UserRepository;

@Service
@Data
public class UserService {

    private final UserRepository userRepo;
    
    @Autowired
    public UserService(UserRepository userRepo){
        this.userRepo = userRepo;
    }
    
    public Optional<UserDTO> saveUser(User user) throws IllegalArgumentException{
        try{
            if(user.getFirstName() == null || user.getMail() == null || user.getLastName() == null){
                throw new IllegalArgumentException("'user' object in saveUser(User user) misses arguments");
            }
            User usr = userRepo.save(user);
            Optional<UserDTO> userdto = Optional.ofNullable(User.toDTO(usr));;
            return userdto;
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException("BAD USER SAVE ACTION");
        }
    }
}
