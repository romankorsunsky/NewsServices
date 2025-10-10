package none.romank.authserv;

import org.springframework.beans.factory.annotation.Autowired;

public class UserRegisterService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;


    public UserDTO addUser(User user) {
        UserDTO dto = new UserDTO(userDetailsRepository.save(user));
        return dto;
    }

}
