package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Patient;
import nimblix.in.HealthCareHub.response.PatientMedicalHistoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    // Get only active patients
//    List<Patient> findByIsDeletedFalse();
    //  Sneha
    Optional<Patient> findByEmail(String email);

    Optional<Patient> findByPhone(String phone);
    @Query("""
SELECT new nimblix.in.HealthCareHub.response.PatientMedicalHistoryResponse(
p.id,
p.name,
p.age,
p.gender,
p.disease,
d.name,
h.name,
h.id,
u.email,
du.email,
lr.testName,
lr.result,
m.medicineName,
pr.instructions
)
FROM Patient p
LEFT JOIN p.user u
LEFT JOIN p.hospital h
LEFT JOIN Prescription pr ON pr.patient.id = p.id
LEFT JOIN pr.doctor d
LEFT JOIN d.user du
LEFT JOIN LabResult lr ON lr.patientId = p.id
LEFT JOIN Medicine m ON m.hospital.id = h.id
WHERE p.id = :id
""")
    List<PatientMedicalHistoryResponse> getPatientMedicalHistory(Long id);
    @Query("""

                SELECT new map(
                p.admissionDate as date,
                COUNT(p.id) as admissions,
                SUM(
                CASE WHEN p.dischargeDate IS NOT NULL
                THEN 1 ELSE 0 END
    )
                as discharges

    )

    FROM Patient p

    WHERE p.admissionDate >= :startDate

    GROUP BY p.admissionDate

    ORDER BY p.admissionDate

    """)
    List<Map<String ,Object>> getAdmissionsDischargesLast14Days(LocalDate startDate);

    @Query("""
    SELECT new map(
     p.admissionDate as date,
     SUM(CASE WHEN p.surgeryRequired = true THEN 1 ELSE 0 END) as surgeries,
     SUM(CASE WHEN p.emergencyCase = true THEN 1 ELSE 0 END) as emergencies
    )
    FROM Patient p
    WHERE p.admissionDate >= :startDate
    GROUP BY p.admissionDate
    ORDER BY p.admissionDate
    """)
    List<Map<String,Object>> getSurgeriesEmergenciesLast14Days(LocalDate startDate);


    @Query("""
SELECT new map(
    p.id as id,
    p.name as name,
    p.phone as phone,
    p.gender as gender,
    COALESCE(h.name, 'N/A') as hospitalName
)
FROM Patient p
LEFT JOIN p.hospital h
ORDER BY p.name
""")
    List<Map<String,Object>> getAllPatients();


    @Query("""

SELECT new map(

p.id as id,
p.name as name,
p.phone as phone,
p.gender as gender,
p.hospital.name as hospitalName,
p.admissionDate as admissionDate

)

FROM Patient p

WHERE p.id=:id

""")
    Map<String,Object> getPatientById(Long id);

    // Custom query for email OR phone
    @Query("SELECT p FROM Patient p WHERE p.email = :email OR p.phone = :phone")
    Optional<Patient> findByEmailOrPhone(@Param("email") String email, @Param("phone") String phone);
}
