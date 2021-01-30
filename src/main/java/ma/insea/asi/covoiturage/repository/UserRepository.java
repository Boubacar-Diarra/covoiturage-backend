package ma.insea.asi.covoiturage.repository;

import ma.insea.asi.covoiturage.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String userName);
}
