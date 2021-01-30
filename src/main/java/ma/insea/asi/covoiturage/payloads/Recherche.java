package ma.insea.asi.covoiturage.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recherche {
    private String prix;
    private String lieuDepart;
    private String lieuArrivee;
    private Date dateDepart;
    private String typeVoiture;
    private String escales;
}
