package ma.insea.asi.covoiturage.repository;

import ma.insea.asi.covoiturage.models.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Long> {
}
