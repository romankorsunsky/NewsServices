package none.romank.backend.api.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=true)
public class AuthorDTO extends UserDTO{
    private String pfpUri;
    private String bio;

    public AuthorDTO(User user,String pfpUri,String bio){
        super(user);
        this.pfpUri = pfpUri;
        this.bio = bio;
    }
}
