package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Admission;
import nimblix.in.HealthCareHub.response.CurePercentageResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    // Check if patient already has an active admission
    boolean existsByPatientIdAndStatus(Long patientId, String status);

    // Check if room already has an active admission
    boolean existsByRoomIdAndStatus(Long roomId, String status);

    // Get admission history by patient
    List<Admission> findByPatientId(Long patientId);

    // Get admission history by patient and status
    List<Admission> findByPatientIdAndStatus(Long patientId, String status);

    // Cure percentage by doctor
    @Query("""
        SELECT new nimblix.in.HealthCareHub.response.CurePercentageResponse(
            a.doctorId,
            COUNT(a),
            SUM(CASE WHEN a.status = 'DISCHARGED' THEN 1 ELSE 0 END),
            (SUM(CASE WHEN a.status = 'DISCHARGED' THEN 1 ELSE 0 END) * 100.0 / COUNT(a))
        )
        FROM Admission a
        WHERE a.doctorId = :doctorId
    """)
    Optional<CurePercentageResponse> getCurePercentage(@Param("doctorId") Long doctorId);

    // Dashboard admissions/discharges last 14 days
    @Query(value = """
        SELECT 
            DATE(admission_date) AS day,
            COUNT(CASE WHEN status = 'ADMITTED' THEN 1 END) AS admissions,
            COUNT(CASE WHEN status = 'DISCHARGED' THEN 1 END) AS discharges
        FROM admissions
        WHERE admission_date >= :startDate
        GROUP BY DATE(admission_date)
        ORDER BY DATE(admission_date)
    """, nativeQuery = true)
    List<Map<String, Object>> getAdmissionsDischargesLast14Days(@Param("startDate") LocalDate startDate);

    @Query(value = """
    SELECT
        DAYNAME(a.admission_date) AS day,
        SUM(CASE WHEN a.status = 'ADMITTED' THEN 1 ELSE 0 END) AS admissions,
        SUM(CASE WHEN a.status = 'DISCHARGED' THEN 1 ELSE 0 END) AS discharges
    FROM admissions a
    JOIN patients p ON a.patient_id = p.id
    WHERE p.hospital_id = :hospitalId
      AND a.admission_date >= CURDATE() - INTERVAL 7 DAY
    GROUP BY DAYOFWEEK(a.admission_date), DAYNAME(a.admission_date)
    ORDER BY DAYOFWEEK(a.admission_date)
    """, nativeQuery = true)
    List<Map<String,Object>> getWeeklyAdmissions(@Param("hospitalId") Long hospitalId);
}