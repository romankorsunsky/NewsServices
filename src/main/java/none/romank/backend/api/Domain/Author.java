package none.romank.backend.api.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
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
}
