package none.romank.authserv;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * This config is specific to the aspect of the app that is responsible for
 * the authorization server also being a client.
 * The AS(Authorization Server) has endpoints for UserRegistration processing, so after a user
 * registers, the Authorization Server propagates the new user to the resource server (backend-service)
 * so it acts as a client.
 * 
 * TL:DR - config for registration logic.
 */
@Configuration
public class ClientAspectConfig {

    @Bean
    public UserRegistrationValidator userRegistrationValidator(){
        return UserRegistrationValidatorImpl.getInstance();
    }
}
