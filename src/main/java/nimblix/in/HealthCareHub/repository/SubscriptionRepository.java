package nimblix.in.HealthCareHub.repository;


import nimblix.in.HealthCareHub.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByHospital_Id(Long hospitalId);

    List<Subscription> findByEndDateBetween(LocalDate startDate, LocalDate endDate);



    boolean existsByHospitalId(Long hospitalId);

    @Query("""
            SELECT s
            FROM Subscription s
            WHERE s.endDate >= CURRENT_DATE
            """)
    List<Subscription> findActiveSubscriptions();

}
