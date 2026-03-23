package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine, Long>{
        Optional<Medicine> findByMedicineNameAndHospital(String medicineName, Hospital hospital);

//  Mimanshu
    @Query("""
        SELECT m.medicineName
        FROM Medicine m
        WHERE m.hospital.id = :hospitalId
        """)
    List<String> getMedicines(Long hospitalId);
    }

