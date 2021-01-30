package ma.insea.asi.covoiturage.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Voiture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
    private String marque;

    @OneToMany(targetEntity = Offre.class, mappedBy="voiture")
    private Set<Offre> offres;
}
