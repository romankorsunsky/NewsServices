package none.romank.authserv;

import lombok.Data;

@Data
public class UserRegistration {
    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;
    
    public UserRegistration(String userName,String password,String email,String firstName,String lastName){
        //who cares about null checks anyway :D, fix it !!!!
        this.username = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
