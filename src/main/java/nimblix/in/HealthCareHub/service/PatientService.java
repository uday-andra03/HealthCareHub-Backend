package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.model.Patient;
import nimblix.in.HealthCareHub.model.Prescription;
import nimblix.in.HealthCareHub.model.PrescriptionMedicines;
import nimblix.in.HealthCareHub.model.Review;
import nimblix.in.HealthCareHub.request.PatientRegisterRequest;
import nimblix.in.HealthCareHub.response.ApiResponse;
import nimblix.in.HealthCareHub.response.PatientMedicalHistoryResponse;
import org.springframework.http.ResponseEntity;
import nimblix.in.HealthCareHub.request.PatientRequest;
import nimblix.in.HealthCareHub.request.UserRegistrationRequest;

import java.util.List;

public interface PatientService {
    Review addDoctorReview(Long patientId, Long doctorId, String comment, int rating) ;
    Patient registerPatient(PatientRegisterRequest request);
    ApiResponse<Object> logout(String authHeader) ;
    ApiResponse forgotPassword(String phoneNumber, String email);
    Review addHospitalReview(Long patientId, Long hospitalId, String comment, int rating) ;
    List<Review> getDoctorReviews(Long doctorId);
    ApiResponse resetPassword(String phoneNumber, String email, String newPassword);
    //   Meghana
    ResponseEntity<ApiResponse<Patient>> updatePatient(Long patientId, UserRegistrationRequest request);

    ApiResponse<Object> login(PatientRequest request);
    ResponseEntity<ApiResponse<Prescription>> getPrescription(Long id);
    ResponseEntity<ApiResponse<PrescriptionMedicines>> getPrescriptionMedicines(Long prescription_id);
    PatientMedicalHistoryResponse getPatientMedicalHistory(Long patientId);
    boolean softDeletePatient(Long id);
}
