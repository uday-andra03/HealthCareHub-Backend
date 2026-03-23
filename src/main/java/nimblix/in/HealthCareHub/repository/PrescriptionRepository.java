package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
//addprescription
    @Query("""
        SELECT p
        FROM Prescription p
        JOIN p.doctor d
        JOIN p.patient pt
        WHERE d.id = :doctorId
    """)
    List<Prescription> findPrescriptionsByDoctorId(@Param("doctorId") Long doctorId);
//patienthistory

    @Query("SELECT p FROM Prescription p WHERE p.doctor.id = :doctorId")
    List<Prescription> findPatientsByDoctorId(@Param("doctorId") Long doctorId);

}


