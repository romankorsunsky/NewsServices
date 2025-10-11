package none.romank.authserv;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Qualifier("userRegistrationService")
public class UserRegisterService {
    private UserDetailsRepository userDetailsRepository;
    private OAuth2AuthorizedClientManager manager;
    
    @Autowired
    public UserRegisterService(UserDetailsRepository userDetailsRepository,
        OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager){
        this.userDetailsRepository = userDetailsRepository;
        this.manager = oAuth2AuthorizedClientManager;
    }

    public UserDTO addUser(UserRegistration user) {
        User actualUser;
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        actualUser = new User();
        actualUser.setUsername(user.getUsername());
        actualUser.setPassword(encoder.encode(user.getPassword()));
        actualUser.setFirstName(user.getFirstName());
        actualUser.setLastName(user.getLastName());
        actualUser.setEmail(user.getEmail());
        actualUser.setCreatedAt(LocalDate.now());
        actualUser.setEnabled(true);
        actualUser.setRoles(Arrays.asList("ROLE_USER"));
        
        UserDTO dto = new UserDTO(userDetailsRepository.save(actualUser));
        System.out.println("THE USER DTO LOOKS LIKE THIS:" + dto.toString());
        RestClient restClient = RestClient.builder().baseUrl("http://localhost:8080/api/users").build();
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.
            withClientRegistrationId("authorization-server-client").
            principal("authentication-server-sync-agent").
            build();

        OAuth2AuthorizedClient authorizedClient = manager.authorize(authorizeRequest);

        //there is a nullable annotation so can return nukl
        if(authorizedClient == null){
            throw new NullPointerException("Couldn't authorize client ,got NULL");
        }

        String token = authorizedClient.getAccessToken().getTokenValue();
        System.out.println("THE TOKEN LOOKS LIKE THIS: " + token);
        ResponseEntity<Void> respEnt = restClient.post().
        uri("/sync").
        header(HttpHeaders.AUTHORIZATION, "Bearer " + token).
        contentType(MediaType.APPLICATION_JSON).
        body(dto).
        retrieve().
        toBodilessEntity();
        return dto;
    }

}
