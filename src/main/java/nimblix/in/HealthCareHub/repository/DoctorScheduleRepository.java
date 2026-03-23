package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.DoctorSchedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
    boolean existsByDoctor_IdAndPatient_IdAndOperationDate(
            Long doctorId,
            Long patientId,
            String operationDate
    );
  List<DoctorSchedule> findAllByDoctor_Id(@Param("doctorId") Long doctorId);
}
