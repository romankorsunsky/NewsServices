package none.romank.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name="AuthorityGrantRequst")
@Entity
public class AuthorityGrantRequest {
    
    @Column(name="id")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="username")
    private String username;

    @Column(name="description")
    private String description;

    public AuthorityGrantRequest(){

    }
    public AuthorityGrantRequest(Integer id,String username,String description){
        this.id = id;
        this.username = username;
        this.description = description;
    }

}
