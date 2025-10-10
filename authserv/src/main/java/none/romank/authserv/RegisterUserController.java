package none.romank.authserv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;


@RestController
public class RegisterUserController {

    private UserRegisterService userRegService;
    
    @Autowired
    public RegisterUserController(@Qualifier("uds") UserRegisterService userRegService){
        this.userRegService = userRegService;
    }
    @PostMapping("/newuser")
    public ResponseEntity<Void> postMethodName(@RequestBody User user) {
        RestClient restClient = RestClient.builder().
            baseUrl("http://localhost:8080/api/usrs/syncuser").build();
        ResponseEntity<Void> resp = restClient.post().
            contentType(MediaType.APPLICATION_JSON).
            body(userRegService.addUser(user)).retrieve().toBodilessEntity();
        return resp;
    }
    
}
