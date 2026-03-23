package nimblix.in.HealthCareHub.repository;


import nimblix.in.HealthCareHub.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""

    SELECT 
    a.appointmentDateTime,
    COUNT(a.id)

    FROM Appointment a

    WHERE a.doctor.hospital.id = :hospitalId

    GROUP BY a.appointmentDateTime

    """)
    List<Object[]> getWeeklyAdmissions(
            Long hospitalId);
}
