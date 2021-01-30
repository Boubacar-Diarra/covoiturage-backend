package ma.insea.asi.covoiturage.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateDemande;
    private String etat;

    @ManyToOne(targetEntity = User.class)
    private User demandeur;

    @ManyToOne(targetEntity = Offre.class)
    private Offre offre;

    public Demande(Date dateDemande, String etat, User demandeur, Offre offre) {
        this.dateDemande = dateDemande;
        this.etat = etat;
        this.demandeur = demandeur;
        this.offre = offre;
    }
}
