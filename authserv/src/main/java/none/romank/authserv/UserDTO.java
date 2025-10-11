package none.romank.authserv;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {
    private String username;
    
    private String email;

    private String firstName;

    private String lastName;

    public UserDTO(){

    }
    public UserDTO(User user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}
