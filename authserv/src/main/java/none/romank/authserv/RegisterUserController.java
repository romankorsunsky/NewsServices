package none.romank.authserv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegisterUserController {

    private UserRegisterService userRegService;
    
    @Autowired
    public RegisterUserController(@Qualifier("userRegistrationService") UserRegisterService userRegService){
        this.userRegService = userRegService;
    }
    @PostMapping("/adduser")
    public ResponseEntity<UserDTO> postMethodName(@RequestBody UserRegistration userRegistration) {
        System.out.println("==============================================================");
        UserDTO dto = userRegService.addUser(userRegistration);
        return ResponseEntity.ok().body(dto);
    }
    
}
