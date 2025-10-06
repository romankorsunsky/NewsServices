package none.romank.backend.api.Domain;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String mail;
    private LocalDate createdAt;

    public UserDTO(Long id,String firstName,String lastName,String email,LocalDate date){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = email;
        this.createdAt = date;
    }

    public UserDTO(User other){
        if(other != null){
            this.id = other.getId();
            this.firstName = other.getFirstName();
            this.lastName = other.getLastName();
            this.mail = other.getMail();
            this.createdAt = other.getCreatedAt();
        }
    }
}
