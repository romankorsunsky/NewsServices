package none.romank.backend;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
@Entity
@Table(name="Account")
@NoArgsConstructor
public class User implements UserDetails{

    @Id
    //@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="acc_id_gen")
    //@SequenceGenerator(name="acc_id_gen",sequenceName="account_id_seq",allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="username")
    private String userName;

    @Column(name="password")
    private String password;

    @Column(name="firstname")
    private String firstName;

    @Column(name="lastname")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="created_at")
    private LocalDate dateOfRegistration;

    @Column(name="enabled")
    private Boolean enabled;

    @Column(name="credits")
    private Integer credits;

    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="account_role",
        joinColumns = @JoinColumn(name = "acc_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy="account",fetch=FetchType.LAZY)
    private List<Transaction> transactions;

    public User(String userName,String password,String firstName,String lastName,String email,LocalDate date,Boolean enabled){
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfRegistration = date;
        this.enabled = enabled;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> lst = roles.stream().map(
            r -> {return new SimpleGrantedAuthority("ROLE_" + r.getRoleName());})
            .collect(Collectors.toList());
        log.info("User " + this.userName + " has roles:");
        lst.forEach(r -> {log.info(r.getAuthority());});
        return lst;
    }
    @Override 
    public boolean isEnabled(){
        return enabled;
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
    public String getUsername(){
        return userName;
    }
    @Override
    public String getPassword(){
        return password;
    }
    @Override
    public int hashCode(){
        return userName.hashCode();
    }

    @Override
    public boolean equals(Object other){
        if(this == other){
            return true;
        }
        if(other == null){
            return false;
        }
        if(this.getClass() != other.getClass()){
            return false;
        }
        User oth = (User)other;

        return (userName.equals(oth.getUsername()));
    }

}
