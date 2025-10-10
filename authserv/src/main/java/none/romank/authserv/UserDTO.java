package none.romank.authserv;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
    private String username;

    private String email;

    private Boolean enabled;

    private String firstName;

    private String lastName;

    private LocalDate createdAt;

    private List<String> roles;

    public UserDTO(User user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.roles = new ArrayList<>(user.getRoles());
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}
