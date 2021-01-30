package ma.insea.asi.covoiturage.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float prix;
    private int nbrePlace;
    private boolean estConducteur;
    private String lieuDepart;
    private String lieuArrivee;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDepart;
    private boolean alternance;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private User offreur;

    /*
    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "offre_demandeurs")
    private Set<User> demandeurs;
     */

    @OneToMany(targetEntity = Escale.class, mappedBy = "offre", cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Escale> escales = new HashSet<>();

    @ManyToOne(targetEntity = Voiture.class, cascade = {CascadeType.PERSIST})
    private Voiture voiture;

    public Offre( float prix, int nbrePlace, boolean estConducteur, String lieuDepart, String lieuArrivee, Date dateDepart, boolean alternance, User offreur, Set<Escale> escales, Voiture voiture) {
        this.prix = prix;
        this.nbrePlace = nbrePlace;
        this.estConducteur = estConducteur;
        this.lieuDepart = lieuDepart;
        this.lieuArrivee = lieuArrivee;
        this.dateDepart = dateDepart;
        this.alternance = alternance;
        this.offreur = offreur;
        this.escales = escales;
        this.voiture = voiture;
    }

}
