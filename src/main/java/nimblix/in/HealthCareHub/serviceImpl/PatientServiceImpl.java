package nimblix.in.HealthCareHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;
import nimblix.in.HealthCareHub.request.UserRegistrationRequest;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.model.*;
import nimblix.in.HealthCareHub.repository.*;
import nimblix.in.HealthCareHub.request.PatientRequest;
import nimblix.in.HealthCareHub.request.PatientRegisterRequest;
import nimblix.in.HealthCareHub.response.ApiResponse;
import nimblix.in.HealthCareHub.response.PatientMedicalHistoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import nimblix.in.HealthCareHub.service.PatientService;
import nimblix.in.HealthCareHub.exception.UserNotFoundException;
import java.util.List;
import java.util.Optional;
import nimblix.in.HealthCareHub.security.JwtUtil;
import java.util.Set;


@Service
@RequiredArgsConstructor

public class PatientServiceImpl implements PatientService {
    private final UserRepository userRepository;
    private final PatientRepository patientrepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMedicinesRepository prescriptionMedicinesRepository;
    private final LabResultRepository labResultRepository;
    private final MedicineRepository medicineRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;


    private final JwtUtil jwtUtil;



    @Override
    public ResponseEntity<ApiResponse<Patient>> updatePatient(Long patientId, UserRegistrationRequest request) {

        Patient patient = patientrepository.findById(patientId).orElse(null);

        ApiResponse<Patient> response = new ApiResponse<>();

        if (patient == null) {

            response.setStatus("FAILURE");
            response.setMessage("Patient not found");
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

//        patient.setName(request.getEmail()); // example update

        if (request.getEmail() != null) {
            patient.setEmail(request.getEmail());
        }

        if (request.getPhoneNumber() != null) {
            patient.setPhone(request.getPhoneNumber());
        }

        patientrepository.save(patient);

        response.setStatus("SUCCESS");
        response.setMessage("Patient updated successfully");
        response.setData(patient);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    public Patient savePatient(Patient patient) {
        return patientrepository.save(patient);
    }
    // PATIENT REGISTRATION
    public Patient registerPatient(PatientRegisterRequest request) {

        // First name validation
        if (!StringUtils.hasText(request.getFirstName())) {
            throw new RuntimeException("Please enter first name");
        }

        // Gender validation
        if (!StringUtils.hasText(request.getGender())) {
            throw new RuntimeException("Please select gender");
        }

        // Email validation
        if (!StringUtils.hasText(request.getEmail())) {
            throw new RuntimeException("Email is required");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Password validation
        if (!StringUtils.hasText(request.getPassword())) {
            throw new RuntimeException("Password cannot be empty");
        }

        // Confirm password check
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Password and confirm password do not match");
        }

        // Create User
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        userRepository.save(user);

        // Create Patient
        Patient patient = Patient.builder()
                .name(request.getFirstName() + " " + request.getLastName())
                .gender(request.getGender())
                .phone(request.getPhoneNumber())
                .email(request.getEmail())   //✅updated these 2 lines
                .role("PATIENT")
                .user(user)
                .build();

        Patient savedPatient = patientrepository.save(patient);

// Hide password in response
        if (savedPatient.getUser() != null) {
            savedPatient.getUser().setPassword(null);
        }

        return savedPatient;
    }

    @Override
    public ApiResponse<Object> login(PatientRequest request) {
        Patient patient = patientrepository.findByEmailOrPhone(request.getEmail(), request.getPhone())
                .orElseThrow(() -> new UserNotFoundException("Patient not found"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(patient.getEmail())
                .password("")
                .authorities(patient.getRole())
                .build();

        String token = jwtUtil.generateToken(userDetails);

        Map<String, Object> data = new HashMap<>();
        data.put("userId", patient.getId());
        data.put("role", patient.getRole());
        data.put("token", token);

        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(HealthCareConstants.STATUS_SUCCESS);
        response.setMessage(HealthCareConstants.LOGIN_SUCCESS);
        response.setData(data);

        return response;
    }
    @Override
    public ApiResponse forgotPassword(String phoneNumber, String email) {

        ApiResponse response = new ApiResponse();

        Optional<User> userOptional;

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            userOptional = userRepository.findByPhoneNumber(phoneNumber);
        } else if (email != null && !email.isEmpty()) {
            userOptional = userRepository.findByEmail(email);
        } else {
            response.setStatus("FAILURE");
            response.setMessage("Phone number or email required");
            return response;
        }

        if (!userOptional.isPresent()) {
            response.setStatus("FAILURE");
            response.setMessage("User not found");
            return response;
        }

        response.setStatus("SUCCESS");
        response.setMessage("User verified. You can reset password.");

        return response;
    }

    @Override
    public ApiResponse resetPassword(String phoneNumber, String email, String newPassword) {

        ApiResponse response = new ApiResponse();

        // Check password
        if (newPassword == null || newPassword.isEmpty()) {
            response.setStatus("FAILURE");
            response.setMessage("New password required");
            return response;
        }

        Optional<User> userOptional;

        // Check user by phone or email
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            userOptional = userRepository.findByPhoneNumber(phoneNumber);
        } else if (email != null && !email.isEmpty()) {
            userOptional = userRepository.findByEmail(email);
        } else {
            response.setStatus("FAILURE");
            response.setMessage("Phone number or email required");
            return response;
        }

        // User not found
        if (!userOptional.isPresent()) {
            response.setStatus("FAILURE");
            response.setMessage("User not found");
            return response;
        }

        // Update password
        User user = userOptional.get();
        user.setPassword(newPassword);

        userRepository.save(user);

        response.setStatus("SUCCESS");
        response.setMessage("Password reset successfully");

        return response;
    }
    public ResponseEntity<ApiResponse<Prescription>> getPrescription(Long id) {
        Optional<Prescription> op = prescriptionRepository.findById(id);

        if (op.isPresent()) {
            Prescription pr = op.get();
            ApiResponse<Prescription> response =new ApiResponse<>();
            response.setData(pr);
            response.setStatus(HealthCareConstants.STATUS_SUCCESS);
            response.setMessage(HealthCareConstants.FETCH_SUCCESS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<Prescription> response =new ApiResponse<>();
            response.setData(null);
            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.FETCH_FAILURE);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //  SIMON
    public ResponseEntity<ApiResponse<PrescriptionMedicines>> getPrescriptionMedicines(Long prescription_id) {

        List<PrescriptionMedicines> prescriptions = prescriptionMedicinesRepository.findByPrescriptionId_Id(prescription_id);

        if (!prescriptions.isEmpty()) {
            ApiResponse<PrescriptionMedicines> response = new ApiResponse<>();
            response.setData((PrescriptionMedicines) prescriptions);
            response.setStatus(HealthCareConstants.STATUS_SUCCESS);
            response.setMessage(HealthCareConstants.FETCH_SUCCESS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<PrescriptionMedicines> response = new ApiResponse<>();
            response.setData(null);
            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.FETCH_FAILURE);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public PatientMedicalHistoryResponse getPatientMedicalHistory(Long patientId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Set<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        boolean isPatient = roles.contains(Role.PATIENT.name());
        boolean isDoctor = roles.contains(Role.DOCTOR.name());

        List<PatientMedicalHistoryResponse> responses = patientrepository.getPatientMedicalHistory(patientId);

        if (responses == null || responses.isEmpty()) {
            return null;
        }

        PatientMedicalHistoryResponse response = responses.get(0);

        if (isPatient && !username.equals(response.getPatientEmail())) {
            throw new AccessDeniedException("Cannot view other patient's history");
        }

        if (isDoctor && !username.equals(response.getDoctorEmail())) {
            throw new AccessDeniedException("Patient not assigned to this doctor");
        }

        List<Object[]> labResults = labResultRepository.getLabResults(patientId);
        if (!labResults.isEmpty()) {
            Object[] lab = labResults.get(0);
            response.setTestName((String) lab[0]);
            response.setTestResult((String) lab[1]);
        } else {
            response.setTestName("");
            response.setTestResult("");
        }

        if (response.getHospitalId() != null) {
            List<String> medicines = medicineRepository.getMedicines(response.getHospitalId());

            if (!medicines.isEmpty()) {
                response.setMedicineName(String.join(", ", medicines));
            } else {
                response.setMedicineName("");
            }
        } else {
            response.setMedicineName("");
        }

        return response;
    }

    //    Yamini
    @Override
    public boolean softDeletePatient(Long id) {
        Patient patient = patientrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if(Boolean.TRUE.equals(patient.getDeleted())) {
            return false;

        }
        patient.setDeleted(true);

        patientrepository.save(patient);

        return true;
    }
    //  Raksha
    @Override
    public Review addDoctorReview(Long patientId, Long doctorId, String comment, int rating) {

        Patient patient = patientrepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Review review = Review.builder()
                .patient(patient)
                .doctor(doctor)
                .comment(comment)
                .rating(rating)
                .build();

        if (doctor.getReviews() == null) {
            doctor.setReviews(new ArrayList<>());
        }

        doctor.getReviews().add(review);

        doctorRepository.save(doctor);

        return review;
    }

    @Override
    public List<Review> getDoctorReviews(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (doctor.getReviews() == null) {
            return new ArrayList<>();
        }

        return doctor.getReviews();
    }

    @Override
    public Review addHospitalReview(Long patientId, Long hospitalId, String comment, int rating) {

        Patient patient = patientrepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        Review review = Review.builder()
                .patient(patient)
                .hospital(hospital)
                .comment(comment)
                .rating(rating)
                .build();

        if (hospital.getReviews() == null) {
            hospital.setReviews(new ArrayList<>());
        }

        hospital.getReviews().add(review);

        hospitalRepository.save(hospital);

        return review;
    }
    //    Udhaya
    @Override
    public ApiResponse<Object> logout(String authHeader) {

        ApiResponse<Object> response = new ApiResponse<>();

        if (authHeader == null || authHeader.isEmpty()) {
            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage("Authorization header missing");
            response.setData(null);
        } else if (!authHeader.startsWith("Bearer ")) {
            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage("Invalid token format");
            response.setData(null);
        } else {
            response.setStatus(HealthCareConstants.STATUS_SUCCESS);
            response.setMessage("Logout successful");
            response.setData(null);
        }

        return response;
    }


}