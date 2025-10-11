package none.romank.authserv;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.Data;


@Component
@Data
public class AppStartActions implements ApplicationListener<ApplicationReadyEvent>{

    @Override
    public void onApplicationEvent(ApplicationReadyEvent e){
        //Test if registration works
        RestClient rc = RestClient.builder().baseUrl("http://localhost:9000/adduser").build();
        String username = "user3";
        String password = "youwillneverfind";
        String email = "some-email@gmail.com";
        String firstName = "Sauron";
        String lastName = "The Evil";
        UserRegistration reg = new UserRegistration(username,password,email,firstName,lastName);

        ResponseEntity<Void> resp = rc.post().
        contentType(MediaType.APPLICATION_JSON).
        body(reg).
        retrieve().
        toEntity(Void.class);
        
    }
}
