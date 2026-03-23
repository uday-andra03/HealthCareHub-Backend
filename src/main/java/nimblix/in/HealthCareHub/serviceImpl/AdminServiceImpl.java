package nimblix.in.HealthCareHub.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.model.Patient;
import nimblix.in.HealthCareHub.model.Specialization;
import nimblix.in.HealthCareHub.repository.*;
import nimblix.in.HealthCareHub.service.AdminService;
import org.springframework.stereotype.Service;
import nimblix.in.HealthCareHub.model.Hospital;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional

public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final ReviewRepository reviewRepository;
    private final PatientRepository patientRepository;
    private final SpecializationRepository specializationRepository;



    @Override
    public Map<String,Object> getAllUsers(){
        List<Map<String,Object>> users =
                adminRepository.getAllUsers();
        if(users==null || users.isEmpty()){
            throw new RuntimeException(
                    HealthCareConstants.NO_USERS_FOUND);
        }
        Map<String,Object> response = new HashMap<>();

        response.put(HealthCareConstants.USERS, users);

        return response;
    }

    @Override
    public Map<String,Object> getUserById(Long id){

        if(id==null || id<=0)
            throw new IllegalArgumentException(HealthCareConstants.INVALID_USER_ID);

        Map<String,Object> user=
                adminRepository.getUserById(id);

        if(user==null || user.isEmpty())
            throw new RuntimeException(HealthCareConstants.USER_NOT_FOUND);

        return user;

    }

    @Override
    public Map<String,Object> approveDoctor(Long id){

        if(id==null || id<=0)
            throw new IllegalArgumentException(HealthCareConstants.INVALID_DOCTOR_ID);

        Doctor doctor=doctorRepository.findById(id)
                .orElseThrow(()->new RuntimeException(HealthCareConstants.DOCTOR_NOT_FOUND));

        if(HealthCareConstants.DOCTOR_STATUS_APPROVED
                .equalsIgnoreCase(doctor.getDoctorStatus()))
            throw new IllegalArgumentException(
                    HealthCareConstants.DOCTOR_ALREADY_APPROVED);

        doctor.setDoctorStatus(
                HealthCareConstants.DOCTOR_STATUS_APPROVED);

        doctorRepository.save(doctor);

        Map<String,Object> response=new HashMap<>();

        response.put("doctorId",doctor.getId());
        response.put("status",doctor.getDoctorStatus());

        return response;

    }

    @Override
    public Map<String,Object> rejectDoctor(Long id){

        if(id==null || id<=0)
            throw new IllegalArgumentException(HealthCareConstants.INVALID_DOCTOR_ID);

        Doctor doctor=doctorRepository.findById(id)
                .orElseThrow(()->new RuntimeException(HealthCareConstants.DOCTOR_NOT_FOUND));

        if("REJECTED".equalsIgnoreCase(doctor.getDoctorStatus()))
            throw new IllegalArgumentException(HealthCareConstants.DOCTOR_ALREADY_REJECTED);

        doctor.setDoctorStatus(HealthCareConstants.DOCTOR_STATUS_REJECTED);

        doctorRepository.save(doctor);

        Map<String,Object> response=new HashMap<>();

        response.put("doctorId",doctor.getId());
        response.put("status",doctor.getDoctorStatus());

        return response;

    }

    @Override
    public Map<String,Object> deleteDoctor(Long id){

        if(id==null || id<=0)
            throw new IllegalArgumentException(HealthCareConstants.INVALID_DOCTOR_ID);

        Doctor doctor=doctorRepository.findById(id)
                .orElseThrow(()->new RuntimeException(HealthCareConstants.DOCTOR_NOT_FOUND));

        doctorRepository.delete(doctor);

        Map<String,Object> response=new HashMap<>();

        response.put("doctorId",id);
        response.put("status","DELETED");

        return response;

    }

    @Override
    public Map<String,Object> getAllHospitals(){

        List<Map<String,Object>> hospitals=
                hospitalRepository.getAllHospitals();

        if(hospitals==null || hospitals.isEmpty())
            throw new RuntimeException(HealthCareConstants.NO_HOSPITAL_FOUND);

        Map<String,Object> response=new HashMap<>();

        response.put(HealthCareConstants.HOSPITAL_LIST,hospitals);

        return response;

    }

    @Override
    public Map<String,Object> getHospitalById(Long id){

        if(id==null || id<=0)
            throw new IllegalArgumentException(HealthCareConstants.INVALID_HOSPITAL_ID);

        Map<String,Object> hospital=
                hospitalRepository.getHospitalById(id);

        if(hospital==null)
            throw new RuntimeException(HealthCareConstants.HOSPITAL_NOT_FOUND);

        return hospital;

    }

    @Override
    public Map<String,Object> approveHospital(Long id){

        if(id==null || id<=0)
            throw new IllegalArgumentException(HealthCareConstants.INVALID_HOSPITAL_ID);

        Hospital hospital=hospitalRepository.findById(id)
                .orElseThrow(()->new RuntimeException(HealthCareConstants.HOSPITAL_NOT_FOUND));

        hospital.setStatus("APPROVED");
        hospital.setIsActive(true);

        hospitalRepository.save(hospital);

        Map<String,Object> response=new HashMap<>();

        response.put("hospitalId",hospital.getId());
        response.put("status",hospital.getStatus());

        return response;

    }

    @Override
    public Map<String,Object> deleteHospital(Long id){

        if(id==null || id<=0)
            throw new IllegalArgumentException(HealthCareConstants.INVALID_HOSPITAL_ID);

        Hospital hospital=hospitalRepository.findById(id)
                .orElseThrow(()->new RuntimeException(HealthCareConstants.HOSPITAL_NOT_FOUND));

        hospital.setIsActive(false);

        hospitalRepository.save(hospital);

        Map<String,Object> response=new HashMap<>();

        response.put("hospitalId",id);
        response.put("status","DEACTIVATED");

        return response;

    }

    @Override
    public Map<String,Object> getAllPatients(){

        List<Map<String,Object>> patients=
                patientRepository.getAllPatients();

        if(patients==null || patients.isEmpty())
            throw new RuntimeException(HealthCareConstants.NO_PATIENT_FOUND);

        Map<String,Object> response=new HashMap<>();

        response.put(HealthCareConstants.PATIENTS,patients);

        return response;

    }

    @Override
    public Map<String,Object> getPatientById(Long id){

        if(id==null || id<=0)
            throw new IllegalArgumentException(HealthCareConstants.INVALID_PATIENT_ID);

        Map<String,Object> patient=
                patientRepository.getPatientById(id);

        if(patient==null)
            throw new RuntimeException(HealthCareConstants.PATIENT_NOT_FOUND);

        return patient;

    }


    @Override
    public Map<String,Object> deletePatient(Long id){

        if(id==null || id<=0)
            throw new IllegalArgumentException(HealthCareConstants.INVALID_PATIENT_ID);

        Patient patient=patientRepository.findById(id)
                .orElseThrow(()->new RuntimeException(HealthCareConstants.PATIENT_NOT_FOUND));

        reviewRepository.deleteByPatientId(id);

        patientRepository.delete(patient);

        Map<String,Object> response=new HashMap<>();

        response.put("patientId",id);
        response.put("status","DELETED");

        return response;

    }

    @Override
    public Map<String,Object> addSpecialization(Map<String,Object> request){

        String name=(String)request.get("name");

        if(name==null || name.isBlank())
            throw new IllegalArgumentException(HealthCareConstants.INVALID_SPECIALIZATION);

        Specialization specialization=new Specialization();

        specialization.setName(name);

        specializationRepository.save(specialization);

        Map<String,Object> response=new HashMap<>();

        response.put("specializationId",specialization.getId());
        response.put("name",specialization.getName());

        return response;

    }

    @Override
    public Map<String,Object> getAllSpecializations(){

        List<Map<String,Object>> specializations=
                specializationRepository.getAllSpecializations();

        if(specializations==null || specializations.isEmpty())
            throw new RuntimeException(HealthCareConstants.NO_SPECIALIZATION_FOUND);

        Map<String,Object> response=new HashMap<>();

        response.put(HealthCareConstants.SPECIALIZATIONS,specializations);

        return response;

    }

    @Override
    public Map<String,Object> deleteSpecialization(Long id){

        if(id==null || id<=0)
            throw new IllegalArgumentException(HealthCareConstants.INVALID_SPECIALIZATION_ID);

        Specialization specialization=specializationRepository.findById(id)
                .orElseThrow(()->new RuntimeException(HealthCareConstants.SPECIALIZATION_NOT_FOUND));

        doctorRepository.deleteBySpecializationId(id);

        specializationRepository.delete(specialization);

        Map<String,Object> response=new HashMap<>();

        response.put("specializationId",id);
        response.put("status","DELETED");

        return response;

    }
}

