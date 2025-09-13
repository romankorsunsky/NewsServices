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
 
@Configuration
public class SecurityConfig {
    
    @Bean("bCryptEncoder")
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpsec) throws Exception{
        httpsec.authorizeHttpRequests(request ->request.
            requestMatchers("/articles").hasRole(Role.RoleVal.AUTHOR.name()).
            requestMatchers("/","/register","/login","/images/**","/api/**").permitAll().
            anyRequest().authenticated()
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
