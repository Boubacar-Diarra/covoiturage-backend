package ma.insea.asi.covoiturage.repository;

import ma.insea.asi.covoiturage.models.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OffreRepository extends JpaRepository<Offre, Long> {
    List<Offre> findByPrix(float prix);
    List<Offre> findAllByDateDepartGreaterThanOrderByDateDepartAsc (Date date);
}
