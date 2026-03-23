package nimblix.in.HealthCareHub.repository;

import jakarta.transaction.Transactional;
import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.model.DoctorAvailability;
import nimblix.in.HealthCareHub.response.DoctorProfileResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO doctor_availability (doctor_id, available_date, start_time, end_time, is_available) " +
            "VALUES (:doctorId, :availableDate, :startTime, :endTime, :isAvailable)",
            nativeQuery = true)
    void insertAvailability(@Param("doctorId") Long doctorId,
                            @Param("availableDate") String availableDate,
                            @Param("startTime") String startTime,
                            @Param("endTime") String endTime,
                            @Param("isAvailable") boolean isAvailable);

    @Query("SELECT da FROM DoctorAvailability da WHERE da.doctor.id = :doctorId")
    List<DoctorAvailability> findAvailabilityByDoctorId(@Param("doctorId") Long doctorId);

    Optional<Doctor> findByEmailId(String emailId);
    @Query("""
       SELECT COUNT(da) > 0
       FROM DoctorAvailability da
       WHERE da.doctor.id = :doctorId
       AND da.availableDate = :today
       """)
    boolean isDoctorAvailableToday(Long doctorId, LocalDate today);
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Doctor> searchDoctorByName(@Param("name") String name);


    @Query("""
            SELECT new nimblix.in.HealthCareHub.response.DoctorProfileResponse(
                d.id, d.name, d.emailId, d.phone, d.qualification, d.experienceYears,
                s.id, s.name,
                h.id, h.name, h.address, h.city, h.state, h.phone, h.email, h.totalBeds
            )
            FROM Doctor d
            LEFT JOIN d.specialization s
            LEFT JOIN d.hospital h
            WHERE h.id = :hospitalId
            """)
    List<DoctorProfileResponse> findDoctorsByHospitalId(@Param("hospitalId") Long hospitalId);

    Optional<Doctor> findByIdAndHospitalId(Long doctorId, Long hospitalId);
    List<Doctor> findBySpecialization_NameIgnoreCase(String specialization);
    @Query("""
            SELECT new nimblix.in.HealthCareHub.response.DoctorProfileResponse(
                d.id,
                d.name,
                d.emailId,
                d.phone,
                d.qualification,
                d.experienceYears,
                s.id,
                s.name,
                h.id,
                h.name,
                h.address,
                h.city,
                h.state,
                h.phone,
                h.email,
                h.totalBeds
            )
            FROM Doctor d
            LEFT JOIN d.specialization s
            LEFT JOIN d.hospital h
            WHERE d.id = :doctorId
            """)
    Optional<DoctorProfileResponse> findDoctorProfileById(@Param("doctorId") Long doctorId);

    Optional<Doctor> getDoctorById(Long doctorId);
    /* Query used to fetch specialization distribution for dashboard pie chart
It counts number of doctors in each specialization */
    @Query("""

    SELECT new map(

    s.name as specialization,

    COUNT(d.id) as doctorCount

    )

    FROM Doctor d

    JOIN d.specialization s

    GROUP BY s.name

    ORDER BY COUNT(d.id) DESC

    """)
    List<Map<String,Object>>
    getSpecializationsDistribution();

    Optional<Doctor> findByPhone(String phone);
    Optional<Doctor> getDoctorProfileById(@Param("doctorId") Long doctorId) ;



    @Query("SELECT COUNT(d) FROM Doctor d WHERE d.isActive = 'ACTIVE'")
    Long countActiveDoctors();

    // Get all doctors by hospital ID
    List<Doctor> findByHospitalId(Long hospitalId);


    @Modifying
    @Query("DELETE FROM Doctor d WHERE d.specialization.id=:id")
    void deleteBySpecializationId(Long id);

}