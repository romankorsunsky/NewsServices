package none.romank.authserv;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean("defaultSecurityFilterChain")
    @Order(2)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpsec) throws Exception{
        return httpsec.
            csrf(csrf->csrf.ignoringRequestMatchers("/adduser/**")).
            authorizeHttpRequests(
            requests -> requests.
            requestMatchers("/oauth2/jwks","/.well-known/**","/adduser/**").permitAll().
            anyRequest().authenticated()).
            formLogin(Customizer.withDefaults()).
            build();
    }
    
    @Bean
    @Qualifier("userDetailsService")
    public UserDetailsService userDetailsService(UserDetailsRepository userDetailsRepository){
        return username -> {
                Optional<User> user = userDetailsRepository.findByUsername(username);
                if(user.isPresent()){
                    return user.get();
                }
                throw new UsernameNotFoundException("Couldn't find user with name" + username);
            };
    }
    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}