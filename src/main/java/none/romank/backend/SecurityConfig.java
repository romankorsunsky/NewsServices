package none.romank.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

 
@Configuration
public class SecurityConfig {
    
    @Bean("bCryptEncoder")
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean("defaultSecurityFilterChain")
    @Order(2)
    SecurityFilterChain filterChain(HttpSecurity httpsec) throws Exception{
        httpsec.csrf(csrf -> csrf.ignoringRequestMatchers("/**")).
            securityMatcher("/**").
            authorizeHttpRequests(request ->request.
            requestMatchers("/articles").hasRole("AUTHOR").
            requestMatchers("/","/register","/login","/images/**","/error"/*"/api/articles/**"*/).permitAll()).
        formLogin(form -> form.
            loginPage("/login").
            loginProcessingUrl("/login").
            defaultSuccessUrl("/")).
        logout(lo -> lo.permitAll());

        return httpsec.build();
    }

    @Bean("resourceServerFilterChain")
    @Order(1)
    SecurityFilterChain resourceServerFilterChain(HttpSecurity httpsec) throws Exception{
        httpsec.csrf(csrf->csrf.disable()).
            securityMatcher("/api/**").
            authorizeHttpRequests(request -> 
            request.
            requestMatchers(HttpMethod.GET,"/api/articles/**").permitAll().
            requestMatchers(HttpMethod.DELETE,"/api/articles/{id}").hasAuthority("SCOPE_delete-article").
            requestMatchers(HttpMethod.POST,"/api/articles/**").hasAuthority("SCOPE_post-article").
            requestMatchers(HttpMethod.PUT,"/api/articles/{id}").hasAuthority("SCOPE_put-article")).
            oauth2ResourceServer(oa2 -> oa2.jwt(Customizer.withDefaults())).
            exceptionHandling(e -> e.accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                    .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint()));

        return httpsec.build();
    }
}
