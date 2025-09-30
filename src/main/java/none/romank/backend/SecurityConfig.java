package none.romank.backend;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import none.romank.backend.api.Domain.Role;
import none.romank.backend.api.Domain.User;
import none.romank.backend.api.Repositories.UserDetailsRepository;
 
@Configuration
public class SecurityConfig {
    
    @Bean("bCryptEncoder")
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpsec) throws Exception{
        httpsec.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**")).
            authorizeHttpRequests(request ->request.
            requestMatchers("/articles").hasRole(Role.RoleVal.AUTHOR.name()).
            requestMatchers("/","/register","/login","/images/**","/api/**","/error").permitAll().
            anyRequest().authenticated() //this last part: anyRequest().authenticated() says "the rest of the paths are for authenticated users"
        ).
        formLogin(form -> form.
            loginPage("/login").
            loginProcessingUrl("/login").
            defaultSuccessUrl("/")).
        logout(lo -> lo.permitAll());

        return httpsec.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserDetailsRepository userRepo){
        return username -> {
            Optional<User> userOpt = userRepo.findByUserName(username);
            User user;
            if(userOpt.isPresent()){
                user = userOpt.get();
                return user;
            }
            throw new UsernameNotFoundException("User " + username + "not found.");
        };
    }
}
