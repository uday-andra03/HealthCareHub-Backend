package nimblix.in.HealthCareHub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.model.Patient;
import nimblix.in.HealthCareHub.model.Prescription;
import nimblix.in.HealthCareHub.model.PrescriptionMedicines;
import nimblix.in.HealthCareHub.model.Review;
import nimblix.in.HealthCareHub.request.AdmitPatientRequest;
import nimblix.in.HealthCareHub.request.PatientRegisterRequest;
import nimblix.in.HealthCareHub.response.AdmitPatientResponse;
import nimblix.in.HealthCareHub.response.AdmitPatientResponse;
import nimblix.in.HealthCareHub.response.ApiResponse;
import nimblix.in.HealthCareHub.response.LabResultResponse;
import nimblix.in.HealthCareHub.service.AdmissionService;
import nimblix.in.HealthCareHub.service.LabResultService;
import nimblix.in.HealthCareHub.response.LabResultResponse;
import nimblix.in.HealthCareHub.response.PatientMedicalHistoryResponse;
import nimblix.in.HealthCareHub.service.PatientService;
import nimblix.in.HealthCareHub.serviceImpl.PatientServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import nimblix.in.HealthCareHub.request.PatientRequest;
import nimblix.in.HealthCareHub.request.UserRegistrationRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final AdmissionService admissionService;
    private final LabResultService labResultService;

    private final PatientServiceImpl patientServiceimpl;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerPatient(
            @RequestBody PatientRegisterRequest request) {

        try {

            Patient patient = patientService.registerPatient(request);

            ApiResponse<Patient> response =
                    new ApiResponse<>(HealthCareConstants.STATUS_SUCCESS, HealthCareConstants.PATIENT_REGISTERED_SUCCESSFULLY, patient);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (RuntimeException e) {

            ApiResponse<Object> response =
                    new ApiResponse<>(HealthCareConstants.STATUS_FAILURE, e.getMessage(), null);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //  Meghana
    @PutMapping("/update/{patientId}")
    public ResponseEntity<ApiResponse<Patient>> updatePatient(
            @PathVariable Long patientId,
            @RequestBody UserRegistrationRequest request) {

        return patientService.updatePatient(patientId, request);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody PatientRegisterRequest request) {

        ApiResponse response = patientService.forgotPassword(
                request.getPhoneNumber(),
                request.getEmail()
        );

        if ("SUCCESS".equals(response.getStatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody PatientRegisterRequest request) {

        ApiResponse response = patientService.resetPassword(
                request.getPhoneNumber(),
                request.getEmail(),
                request.getPassword()
        );

        if ("SUCCESS".equals(response.getStatus())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //  SIMON
    @GetMapping("/get/prescriptions/{id}")
    public ResponseEntity<ApiResponse<Prescription>> getPrescription(@PathVariable Long id){
        return patientService.getPrescription(id);
    }

    @GetMapping("/get/prescriptionmedicine/{prescriptionId}")
    public ResponseEntity<ApiResponse<PrescriptionMedicines>> getPrescriptionMedicine(@PathVariable Long prescriptionId){
        return patientService.getPrescriptionMedicines(prescriptionId);
    }

    @GetMapping("/{id}/medical-history")
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    public ResponseEntity<ApiResponse<PatientMedicalHistoryResponse>> getMedicalHistory(
            @PathVariable Long id) {

        PatientMedicalHistoryResponse medicalHistory =
                patientService.getPatientMedicalHistory(id);

        ApiResponse<PatientMedicalHistoryResponse> response = new ApiResponse<>();

        if (medicalHistory != null) {

            response.setStatus(HealthCareConstants.STATUS_SUCCESS);
            response.setMessage(HealthCareConstants.MEDICAL_HISTORY_FETCHED_SUCCESSFULLY);
            response.setData(medicalHistory);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {

            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.PATIENT_NOT_FOUND_WITH_ID + id);
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //  Yamini
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePatient(@PathVariable Long id) {

        boolean isDeleted = patientService.softDeletePatient(id);

        ApiResponse<Void> response = new ApiResponse<>();

        if(isDeleted) {

            response.setStatus(HealthCareConstants.STATUS_SUCCESS);
            response.setMessage(HealthCareConstants.PATIENT_DELETED_SUCCESSFULLY);
            response.setData(null);

            return ResponseEntity.ok(response);

        } else {

            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.PATIENT_NOT_FOUND_WITH_ID + id);
            response.setData(null);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/admissions/admit")
    public ResponseEntity<Map<String, Object>> admitPatient(
            @RequestBody AdmitPatientRequest request) {

        AdmitPatientResponse data = admissionService.admitPatient(request);

        if (data == null) {
            Map<String, Object> error = new HashMap<>();
            error.put(HealthCareConstants.STATUS, HttpStatus.NOT_FOUND.value());
            error.put(HealthCareConstants.MESSAGE, HealthCareConstants.PATIENT_NOT_FOUND_WITH_ID);

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        Map<String, Object> response = new HashMap<>();
        response.put(HealthCareConstants.STATUS, HttpStatus.CREATED.value());
        response.put(HealthCareConstants.MESSAGE, HealthCareConstants.PATIENT_ADMITTED_SUCCESSFULLY);
        response.put(HealthCareConstants.DATA, data);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Task 186 – Lab Result Endpoint

    // GET api/patient/lab-results/patient/{patientId}
    @GetMapping("/lab-results/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getLabResultsByPatient(
            @PathVariable Long patientId) {

        List<LabResultResponse> data = labResultService.getLabResultsByPatient(patientId);

        if (data == null) {
            Map<String, Object> error = new HashMap<>();
            error.put(HealthCareConstants.STATUS, HttpStatus.NOT_FOUND.value());
            error.put(HealthCareConstants.MESSAGE, HealthCareConstants.PATIENT_NOT_FOUND_WITH_ID + patientId);

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        Map<String, Object> response = new HashMap<>();
        response.put(HealthCareConstants.STATUS, HttpStatus.OK.value());
        response.put(HealthCareConstants.MESSAGE, HealthCareConstants.LAB_RESULT_FETCHED_SUCCESSFULLY);
        response.put(String.valueOf(HealthCareConstants.COUNT), data.size());
        response.put(HealthCareConstants.DATA, data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/doctors/{doctorId}/reviews")
    public ResponseEntity<ApiResponse<List<Review>>> getDoctorReviews(
            @PathVariable Long doctorId) {

        List<Review> data = patientService.getDoctorReviews(doctorId);

        ApiResponse<List<Review>> response = new ApiResponse<>();


        response.setStatus(String.valueOf(HttpStatus.OK.value()));
        response.setMessage("Doctor reviews fetched successfully");
        response.setData(data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{patientId}/hospitals/{hospitalId}/review")
    public ResponseEntity<ApiResponse<Review>> addHospitalReview(
            @PathVariable Long patientId,
            @PathVariable Long hospitalId,
            @RequestBody Review request) {

        Review data = patientService.addHospitalReview(
                patientId,
                hospitalId,
                request.getComment(),
                request.getRating()
        );

        ApiResponse<Review> response = new ApiResponse<>();


        response.setStatus(String.valueOf(HttpStatus.CREATED.value()));
        response.setMessage("Hospital review added successfully");
        response.setData(data);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //    udhaya
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        ApiResponse<Object> response = patientService.logout(authHeader);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //  Sneha
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@Valid @RequestBody PatientRequest request) {
        ApiResponse<Object> response = patientService.login(request);
        return ResponseEntity.ok(response);
    }
}