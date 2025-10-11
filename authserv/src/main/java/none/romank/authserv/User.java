package none.romank.authserv;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection= "users")
public class User implements UserDetails{
    @Id
    private String username;

    private String password;

    private String email;
    
    private String firstName;

    private String lastName;
    
    private Boolean enabled;

    private LocalDate createdAt;

    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(String s: roles){
            authorities.add(new SimpleGrantedAuthority(s));
        }
        return authorities;
    }
    static boolean isOk(User user) {
        SecureRandom rnd = new SecureRandom();
        float num = rnd.nextFloat(0.0f, 1.0f);
        if(num > 0.5f){
            return true;
        }
        return false;
    }


    @Override 
    public boolean isAccountNonExpired(){
        return true;
    }
    @Override 
    public boolean isAccountNonLocked(){
        return true;
    }
    @Override 
    public boolean isCredentialsNonExpired(){
        return true;
    }
    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
