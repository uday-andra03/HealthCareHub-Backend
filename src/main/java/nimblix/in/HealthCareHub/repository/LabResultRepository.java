package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.LabResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabResultRepository extends JpaRepository<LabResult, Long> {

    // Correct for simple Long FK
    List<LabResult> findByPatientId(Long patientId);

    List<LabResult> findByPatientIdAndStatus(Long patientId, String status);

    List<LabResult> findByDoctorId(Long doctorId);

//  Mimanshu
    @Query("""
    SELECT lr.testName, lr.result
    FROM LabResult lr
    WHERE lr.patientId = :patientId
    ORDER BY lr.resultId DESC
    """)
    List<Object[]> getLabResults(Long patientId);
}