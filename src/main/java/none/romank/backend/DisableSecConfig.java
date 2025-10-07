package none.romank.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class DisableSecConfig {
    @Bean
    @Order(0)
    SecurityFilterChain disableSecurityFilterChain(HttpSecurity sec) throws Exception{
        return sec.authorizeHttpRequests(req -> req.anyRequest().permitAll()).
        csrf(csrf -> csrf.disable()).
        build();
    }
}
