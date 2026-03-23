package nimblix.in.HealthCareHub.service;

import java.util.Map;

public interface AdminService {

    Map<String,Object> getAllUsers();

    Map<String,Object> getUserById(Long id);

    Map<String,Object> approveDoctor(Long id);

    Map<String,Object> rejectDoctor(Long id);

    Map<String,Object> deleteDoctor(Long id);

    Map<String,Object> getAllHospitals();

    Map<String,Object> getHospitalById(Long id);

    Map<String,Object> approveHospital(Long id);

    Map<String,Object> deleteHospital(Long id);

    Map<String,Object> getAllPatients();

    Map<String,Object> getPatientById(Long id);

    Map<String,Object> deletePatient(Long id);

    Map<String,Object> addSpecialization(Map<String,Object> request);

    Map<String,Object> getAllSpecializations();

    Map<String,Object> deleteSpecialization(Long id);

}