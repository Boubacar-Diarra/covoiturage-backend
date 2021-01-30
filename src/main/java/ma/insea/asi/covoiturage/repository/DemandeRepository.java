package ma.insea.asi.covoiturage.repository;

import ma.insea.asi.covoiturage.models.Demande;
import ma.insea.asi.covoiturage.models.Offre;
import ma.insea.asi.covoiturage.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
    List<Demande> findByOffre(Offre offre);
    List<Demande> findByDemandeur(User user);
    @Query("select d from  Demande d  where d.demandeur.id = ?1 and  d.offre.id = ?2 ")
    Demande findDemande(Long user, Long offre);
}
