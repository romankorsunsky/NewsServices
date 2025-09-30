package none.romank.backend.api.Domain;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class RegistrationForm {

    private String userName;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String passwordConfirmation;

    public static User of(RegistrationForm rf,PasswordEncoder encoder){
        return new User(rf.userName, encoder.encode(rf.password), rf.firstName, rf.lastName, rf.emailAddress, LocalDate.now(), true);
    }
}
