package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital,Long> {
    Optional<Hospital> findByName(String name);

    @Query("SELECT h FROM Hospital h WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :hospitalName, '%'))")
    List<Hospital> searchHospitalByName(String hospitalName);
    @Query("SELECT SUM(h.totalBeds) FROM Hospital h")
    Long getTotalBeds();

    Optional<Hospital> findByEmail(String email);

    @Query("""
            SELECT new map(
            h.id as id,
            h.name as name,
            h.address as address,
            h.city as city,
            h.state as state,
            h.phone as phone,
            h.email as email,
            h.totalBeds as totalBeds
            )
            FROM Hospital h
            """)
    List<Map<String, Object>> findAllHospitals();
    Optional<Hospital> findByNameAndCityAndState(String name, String city, String state);
    @Query("""
    SELECT new map(

            h.id as id,
            h.name as name

    )

    FROM Hospital h

    ORDER BY h.name

""")
    List<Map<String,Object>> getHospitalDropdownList();


    /*Query used to fetch hospital overview details for dashboard table
    We join hospital and doctor tables to calculate doctor count */
    @Query("""

    SELECT new map(

    h.id as id,

    h.name as name,

    h.city as city,

    h.rating as rating,

    COUNT(d.id) as doctorCount,

    h.totalBeds as totalBeds,

    h.status as status

    )

    FROM Hospital h

    LEFT JOIN Doctor d
    ON d.hospital.id = h.id

    GROUP BY h.id,
             h.name,
             h.city,
             h.rating,
             h.totalBeds,
             h.status

    """)
    List<Map<String,Object>> getHospitalOverview();

    List<Hospital> findByNameContainingIgnoreCase(String name);


    @Query("""

    SELECT new map(

            h.totalBeds as totalBeds,

            COUNT(DISTINCT d.id) as totalDoctors,

    COUNT(DISTINCT p.id) as totalPatients,

    COUNT(DISTINCT s.id) as totalSpecializations

    )

    FROM Hospital h

    LEFT JOIN Doctor d
    ON d.hospital.id = h.id

    LEFT JOIN Patient p
    ON p.hospital.id = h.id

    LEFT JOIN Specialization s
    ON d.specialization.id = s.id

    WHERE h.id = :hospitalId

    """)
    Map<String,Object> getHospitalStatistics(Long hospitalId);




    @Query("SELECT AVG(h.rating) FROM Hospital h")
    Double getAverageRating();



    @Query("""

SELECT new map(

h.id as id,
h.name as name,
h.city as city,
h.totalBeds as totalBeds

)

FROM Hospital h

ORDER BY h.name

""")
    List<Map<String,Object>> getAllHospitals();


    @Query("""

SELECT new map(

h.id as id,
h.name as name,
h.city as city,
h.totalBeds as totalBeds,
h.phone as phone

)

FROM Hospital h

WHERE h.id=:id

""")
    Map<String,Object> getHospitalById(Long id);


    @Query("""

    SELECT DISTINCT new map(

    h.id as id,
    h.name as name,
    h.city as city,
    h.totalBeds as totalBeds,
    s.name as specialization

    )

    FROM Doctor d

    JOIN d.hospital h

    JOIN d.specialization s

    WHERE LOWER(s.name)=LOWER(:specialization)

    ORDER BY h.name

    """)
    List<Map<String,Object>> filterHospitals(String specialization);


}
