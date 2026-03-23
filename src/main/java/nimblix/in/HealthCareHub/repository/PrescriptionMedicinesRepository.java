package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.PrescriptionMedicines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionMedicinesRepository extends JpaRepository<PrescriptionMedicines,Long> {
    List<PrescriptionMedicines> findByPrescriptionId_Id(Long id);
}
