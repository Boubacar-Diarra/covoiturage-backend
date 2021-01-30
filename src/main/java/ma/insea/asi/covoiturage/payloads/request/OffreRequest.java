package ma.insea.asi.covoiturage.payloads.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import ma.insea.asi.covoiturage.models.Escale;
import ma.insea.asi.covoiturage.models.User;
import ma.insea.asi.covoiturage.models.Voiture;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class OffreRequest {
    private Long id;
    private float prix;
    private int nbrePlace;
    private boolean estConducteur;
    private String lieuDepart;
    private String lieuArrivee;
    private Date dateDepart;
    private boolean alternance;
    //private Long offreur;

    /*
    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "offre_demandeurs")
    private Set<User> demandeurs;
     */
    private Set<Escale> escales;

    private Voiture voiture;
}
