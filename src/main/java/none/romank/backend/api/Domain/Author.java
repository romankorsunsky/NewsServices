package none.romank.backend.api.Domain;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="authors")
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    private Long id;

    @Column(name="biography")
    private String bio;

    @Column(name="pfpuri")
    private String pfpUri;

    @OneToOne
    @MapsId
    @JoinColumn(name="id")
    private User user;

    @OneToMany(mappedBy="author",fetch=FetchType.LAZY)
    private Set<Article> articles;

    public static AuthorDTO toDTO(Author author){
        return new AuthorDTO(author.getUser(), author.pfpUri, author.bio);
    }
}
