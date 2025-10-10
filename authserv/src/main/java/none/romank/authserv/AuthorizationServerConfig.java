package none.romank.authserv;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration(proxyBeanMethods=false)
public class AuthorizationServerConfig {
    private static final int KEY_SIZE = 2048;


    @Order(1)
    @Bean("authorizationServerFilterChain")
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity httpsec)
        throws Exception{
            OAuth2AuthorizationServerConfigurer oauthServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();
            
            httpsec
                .securityMatcher(oauthServerConfigurer.getEndpointsMatcher())
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/.well-known/**", "/oauth2/jwks").permitAll()
                    .anyRequest().authenticated()
                )
                .with(oauthServerConfigurer,cstmizer -> cstmizer.oidc(Customizer.withDefaults()))
                .formLogin(Customizer.withDefaults());
        
            return httpsec.build();
    }

    @Bean
    OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() throws Exception{
        return (encodingContext) -> {
            if(OAuth2TokenType.ACCESS_TOKEN.getValue().equals(encodingContext.getTokenType().getValue())){
                Authentication principal = encodingContext.getPrincipal();
                List<String> authorities = 
                    principal.getAuthorities().stream().map(
                        authority -> authority.getAuthority()
                    ).collect(Collectors.toList());
                //Set<String> potentialScopes = encodingContext.getAuthorizedScopes();
                Set<String> allowedScopes = new HashSet<>();
                
                //check if we have author role
               
                if(authorities.contains("ROLE_ADMIN")){
                    allowedScopes.add("get-article");
                    allowedScopes.add("put-article");
                    allowedScopes.add("delete-article");
                    allowedScopes.add("post-article");
                }

                else if(authorities.contains("ROLE_AUTHOR")){
                    allowedScopes.add("get-article");
                    allowedScopes.add("put-article");
                    allowedScopes.add("post-article");
                }

                else if(authorities.contains("ROLE_USER")){
                    allowedScopes.add("get-article");
                }
            
                encodingContext.getClaims().claim("scope",allowedScopes);
            }
        };
    }

    @Bean("inMemoryClientRepo")
    InMemoryRegisteredClientRepository inMemoryRegisteredClientRepository(PasswordEncoder bCryptPasswordEncoder){
        PasswordEncoder encoder = bCryptPasswordEncoder;
        List<RegisteredClient> clients = new ArrayList<>();
        RegisteredClient backendClient = RegisteredClient.withId(UUID.randomUUID().toString()).
            clientId("articles-client").
            clientSecret(encoder.encode("articles-client")).
            clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC).
            authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE).
            authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN).
            redirectUri("http://localhost:9090/login/oauth2/code/articles-client").
            scope("post-article").
            scope("delete-article").
            scope("put-article").
            scope("get-article").
            scope(OidcScopes.OPENID).
            clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build()).
            build();
        RegisteredClient userSyncClient = RegisteredClient.withId(UUID.randomUUID().toString()).
            clientId("authorization-server-client").
            clientSecret("authorization-server-client").
            clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC).
            authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS).
            redirectUri("http://localhost:9090/login/oauth2/code/authorization-server-client").
            scope("sync-users").
            clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build()).
            build();
        clients.add(userSyncClient);
        clients.add(backendClient);
        return new InMemoryRegisteredClientRepository(clients);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException{
        RSAKey rsaKey = generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector,securityContext) -> jwkSelector.select(jwkSet);
    }
    private static RSAKey generateRsa() throws NoSuchAlgorithmException{
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey).
            privateKey(privateKey).
            keyID(UUID.randomUUID().toString()).
            build();
    }

    private static KeyPair generateRsaKey() throws NoSuchAlgorithmException{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource){
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
}