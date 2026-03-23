package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.model.*;
import nimblix.in.HealthCareHub.request.*;
import nimblix.in.HealthCareHub.response.ApiResponse;
import nimblix.in.HealthCareHub.response.CurePercentageResponse;
import nimblix.in.HealthCareHub.response.DoctorProfileResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface DoctorService {
    ApiResponse<Doctor> signIn(DoctorSignInRequest request);
    ApiResponse<String> registerDoctor(DoctorRegistrationRequest request);
    ApiResponse<String> signOut(Long doctorId);
    List<Doctor> getDoctorsByHospital(Long hospitalId);
    List<Map<String, Object>> getDoctorAvailability(Long doctorId);
    ApiResponse<Doctor> getDoctorDetails(Long doctorId, Long hospitalId);
    ApiResponse<String> updateDoctorDetails(DoctorRegistrationRequest request);
    ApiResponse<List<Review>> getDoctorReviews(Long doctorId);
    ApiResponse<String> deleteDoctorDetails(Long doctorId) ;    //Doctor getDoctorById(Long doctorId);
    //ApiResponse<Object> getDoctorProfile(Long doctorId);
    ApiResponse<Map<String, Object>> getPatientsSeen(Long doctorId, Integer month);
    List<Map<String, Object>> getDoctorsByHospitalId(Long hospitalId);
    List<Doctor> filterDoctorsBySpecialization(String specialization);
    String addDoctor(DoctorAddRequest request);
    ApiResponse<List<Doctor>> getDoctorsBySpecialization(String specialization);
    List<Doctor> searchDoctorByName(String name);
    ApiResponse<Doctor> getDoctorById(Long doctorId);
    CurePercentageResponse getCurePercentage(Long doctorId);
    DoctorSchedule createDoctorSchedule(Long doctorId, DoctorScheduleRequest request);
    List<DoctorSchedule> getDoctorSchedules(Long doctorId);
    DoctorSchedule updateDoctorScheduleStatus(Long scheduleId, String status);

    Doctor getDoctorStatus(Long doctorId);
    Doctor updateDoctorStatus(Long doctorId, String status);
    //add prescription
    Prescription addPrescription(PrescriptionRequest request);
    //patienthistory
    ApiResponse<List<Prescription>> getPatientHistoryByDoctorId(Long doctorId);
    DoctorAvailability setDoctorAvailability(DoctorRegistrationRequest request);
    //DoctorProfileResponse getDoctorProfile(Long );
    Object updateDoctor(Long doctorId, Map<String,Object> updates);
}
