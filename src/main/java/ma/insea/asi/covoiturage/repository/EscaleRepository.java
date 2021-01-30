package ma.insea.asi.covoiturage.repository;

import ma.insea.asi.covoiturage.models.Escale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscaleRepository extends JpaRepository<Escale, Long> {
}
