package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Review;
import nimblix.in.HealthCareHub.response.DoctorReviewResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByDoctorId(Long doctorId);
//doctor review and ratings
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.doctor.id = :doctorId")
    Double getAverageRatingByDoctorId(@Param("doctorId") Long doctorId);

    @Query("""
        SELECT new nimblix.in.HealthCareHub.response.DoctorReviewResponse(
        COALESCE(AVG(r.rating),0),
        COUNT(r)
        )
        FROM Review r
        WHERE r.doctor.id = :doctorId
    """)
    DoctorReviewResponse findReviewStatsByDoctorId(@Param("doctorId") Long doctorId);


    @Query("""
    SELECT new map(
    r.id as id,
    r.patient.name as patientName,
    r.rating as rating,
    r.comment as comment,
    r.createdTime as createdTime
    )
    FROM Review r
    WHERE r.hospital.id = :hospitalId
""")
    List<Map<String, Object>> findHospitalReviews(@Param("hospitalId") Long hospitalId);


    @Modifying
    @Query("DELETE FROM Review r WHERE r.patient.id=:id")
    void deleteByPatientId(Long id);


}
