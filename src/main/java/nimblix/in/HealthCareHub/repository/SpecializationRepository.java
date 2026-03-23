package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    Optional<Specialization> findByName(String name);


    @Query("""

SELECT new map(

s.id as id,
s.name as name

)

FROM Specialization s

ORDER BY s.name

""")
    List<Map<String,Object>> getAllSpecializations();

}
