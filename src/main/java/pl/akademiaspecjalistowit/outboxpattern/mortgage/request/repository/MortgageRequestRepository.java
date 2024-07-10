package pl.akademiaspecjalistowit.outboxpattern.mortgage.request.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.akademiaspecjalistowit.outboxpattern.mortgage.request.entity.MortgageRequestEntity;

@Repository
public interface MortgageRequestRepository extends JpaRepository<MortgageRequestEntity, Long> {
    Optional<MortgageRequestEntity> findByTechnicalId(UUID technicalId);
}
