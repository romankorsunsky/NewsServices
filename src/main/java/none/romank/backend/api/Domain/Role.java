package none.romank.backend.api.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="roles")
public class Role {
    @Id
    @Column(name="role_id")
    private Integer id;

    @Column(name="role_name")
    private String roleName;


    public Role(Integer id,String roleName){
        this.id = id;
        this.roleName = roleName;
    }
    public enum RoleVal{ADMIN,USER,AUTHOR,EDITOR};
}
