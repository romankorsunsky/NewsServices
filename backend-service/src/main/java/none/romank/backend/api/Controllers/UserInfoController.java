package none.romank.backend.api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import none.romank.backend.api.Domain.User;
import none.romank.backend.api.Domain.UserDTO;
import none.romank.backend.api.Services.UserService;


@RestController
@RequestMapping("/api/users")
public class UserInfoController {

  
    private final UserService userService;

    @Autowired
    public UserInfoController(UserService userService){
        this.userService = userService;
    }

    /*
     * Here is the authorization endpoint of the Authorization Server propagating a new user to this service.
     */
    @PostMapping("/sync")
    public ResponseEntity<UserDTO> postMethodName(@RequestBody User user) {
        ResponseEntity<UserDTO> response = ResponseEntity.of(userService.saveUser(user));
        return response;
    }
}
