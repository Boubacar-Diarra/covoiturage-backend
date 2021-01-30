package ma.insea.asi.covoiturage.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Escale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lieu;
    private String motif;

    @ManyToOne(targetEntity = Offre.class)
    private Offre offre;
}
