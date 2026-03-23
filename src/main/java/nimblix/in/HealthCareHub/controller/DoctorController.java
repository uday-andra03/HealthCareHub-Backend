package nimblix.in.HealthCareHub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.model.*;
import nimblix.in.HealthCareHub.request.*;
import nimblix.in.HealthCareHub.response.ApiResponse;
import nimblix.in.HealthCareHub.response.CurePercentageResponse;
import nimblix.in.HealthCareHub.response.DoctorProfileResponse;
import nimblix.in.HealthCareHub.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerDoctor(
            @Valid @RequestBody DoctorRegistrationRequest request) {

        ApiResponse<String> response = doctorService.registerDoctor(request);

        if ("SUCCESS".equals(response.getStatus())) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Add a new doctor under hospital
    @PostMapping("/addDoctor")
    public ResponseEntity<ApiResponse<String>> addDoctorToHospital(@RequestBody DoctorAddRequest request) {
        String result = doctorService.addDoctor(request);

        ApiResponse<String> response = new ApiResponse<>(HealthCareConstants.STATUS_SUCCESS,
                (String)HealthCareConstants.DOCTOR_ADDED_SUCCESS,result);

        return ResponseEntity.ok(response);
    }




// Filter doctors by specialization

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<Doctor>>> filterDoctors(
            @RequestParam(required = false) String specialization) {

        ApiResponse<List<Doctor>> response = new ApiResponse<>();

        // Edge case 1: parameter missing
        if (specialization == null) {
            response.setStatus(HealthCareConstants.FAILURE);
            response.setMessage(HealthCareConstants.SPECIALIZATION_REQUIRED);
            response.setData(null);

            return ResponseEntity.badRequest().body(response);
        }

        try {

            List<Doctor> doctors =
                    doctorService.filterDoctorsBySpecialization(specialization);

            response.setStatus(HealthCareConstants.SUCCESS);
            response.setMessage(HealthCareConstants.DOCTORS_FETCHED_SUCCESS);
            response.setData(doctors);

            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {

            response.setStatus(HealthCareConstants.FAILURE);
            response.setMessage(ex.getMessage());
            response.setData(null);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }



    @GetMapping("/getDoctorDetails/{doctorId}/{hospitalId}")
    public ResponseEntity<ApiResponse<Doctor>> getDoctorDetails(
            @PathVariable Long doctorId,
            @PathVariable Long hospitalId) {

        ApiResponse<Doctor> response = doctorService.getDoctorDetails(doctorId, hospitalId);

        if ("SUCCESS".equals(response.getStatus())) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @PutMapping("/updateDoctorDetails")
    public ResponseEntity<ApiResponse<String>> updateDoctorDetails(
            @RequestBody DoctorRegistrationRequest request) {

        ApiResponse<String> response = doctorService.updateDoctorDetails(request);

        if ("SUCCESS".equals(response.getStatus())) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @DeleteMapping("/deleteDoctorDetails")
    public ResponseEntity<ApiResponse<String>> deleteDoctorDetails(
            @RequestParam Long doctorId) {

        ApiResponse<String> response = doctorService.deleteDoctorDetails(doctorId);

        if ("SUCCESS".equals(response.getStatus())) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //  - Get Doctor by ID with edge cases
    @GetMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<Doctor>> getDoctorById(@PathVariable Long doctorId) {

        ApiResponse<Doctor> response = new ApiResponse<>();

        if (doctorId <= 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            response.setMessage(HealthCareConstants.DOCTOR_ID_CANNOT_BE_NULL_ZERO_OR_NEGATIVE);
            response.setData(null);

            return ResponseEntity.badRequest().body(response);
        }

        Doctor doctor = doctorService.getDoctorById(doctorId).getData();

        if (doctor == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            response.setMessage(HealthCareConstants.DOCTOR_NOT_FOUND);
            response.setData(null);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setMessage(HealthCareConstants.DOCTOR_FETCHED_SUCCESSFULLY);
        response.setData(doctor);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{doctorId}/curepercentage")
    public ApiResponse<CurePercentageResponse> getCurePercentage(
            @PathVariable Long doctorId) {

        ApiResponse<CurePercentageResponse> response = new ApiResponse<>();

        if (doctorId == null || doctorId <= 0) {
            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.INVALID_DOCTOR_ID);
            response.setData(null);
            return response;
        }

        CurePercentageResponse cure =
                doctorService.getCurePercentage(doctorId);

        response.setStatus(HealthCareConstants.STATUS_SUCCESS);
        response.setMessage(HealthCareConstants.FETCH_SUCCESS);
        response.setData(cure);

        return response;
    }

    @PostMapping("/{doctorId}/schedule")
    public ResponseEntity<ApiResponse<DoctorSchedule>> createDoctorSchedule(
            @PathVariable Long doctorId,
            @RequestBody DoctorScheduleRequest request) {

        ApiResponse<DoctorSchedule> response = new ApiResponse<>();

        if (doctorId <= 0) {
            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.INVALID_DOCTOR_ID);
            response.setData(null);
            return ResponseEntity.badRequest().body(response);
        }

        DoctorSchedule schedule = doctorService.createDoctorSchedule(doctorId, request);

        if (schedule == null) {
            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.DOCTOR_NOT_FOUND_OR_SCHEDULE_CREATION_FAILED);
            response.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.setStatus(HealthCareConstants.STATUS_SUCCESS);
        response.setMessage(HealthCareConstants.DOCTOR_SCHEDULE_CREATED_SUCCESSFULLY);
        response.setData(schedule);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{doctorId}/schedules")
    public ApiResponse<List<DoctorSchedule>> getSchedules(@PathVariable Long doctorId) {

        ApiResponse<List<DoctorSchedule>> response = new ApiResponse<>();

        if (doctorId <= 0) {
            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.INVALID_DOCTOR_ID);
            response.setData(null);
            return response;
        }

        List<DoctorSchedule> schedules = doctorService.getDoctorSchedules(doctorId);

        if (schedules == null || schedules.isEmpty()) {
            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.NO_SCHEDULES_FOUND_FOR_DOCTOR);
            response.setData(null);
            return response;
        }

        response.setStatus(HealthCareConstants.STATUS_SUCCESS);
        response.setMessage(HealthCareConstants.FETCH_SUCCESS);
        response.setData(schedules);

        return response;
    }

    @PutMapping("/schedule/{scheduleId}/status")
    public ResponseEntity<ApiResponse<DoctorSchedule>> updateScheduleStatus(
            @PathVariable Long scheduleId,
            @RequestParam String status) {

        ApiResponse<DoctorSchedule> response = new ApiResponse<>();

        if (scheduleId <= 0) {
            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.INVALID_DOCTOR_ID);
            response.setData(null);
            return ResponseEntity.badRequest().body(response);
        }

        DoctorSchedule schedule = doctorService.updateDoctorScheduleStatus(scheduleId, status);

        if (schedule == null) {
            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.DOCTOR_NOT_FOUND_OR_SCHEDULE_CREATION_FAILED);
            response.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.setStatus(HealthCareConstants.STATUS_SUCCESS);
        response.setMessage(HealthCareConstants.DOCTOR_SCHEDULE_CREATED_SUCCESSFULLY);
        response.setData(schedule);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/addprescription")
    public ResponseEntity<ApiResponse<Prescription>> addPrescription(@RequestBody PrescriptionRequest request) {

        ApiResponse<Prescription> response = new ApiResponse<>();

        if (request.getDoctorId() == null || request.getDoctorId() <= 0) {
            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.INVALID_DOCTOR_ID);
            response.setData(null);
            return ResponseEntity.badRequest().body(response);

        }

        if (request.getPatientId() == null || request.getPatientId() <= 0) {
            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.INVALID_PATIENT_ID);
            response.setData(null);
            return ResponseEntity.badRequest().body(response);
        }

        Prescription prescription = doctorService.addPrescription(request);


        response.setStatus(HealthCareConstants.STATUS_SUCCESS);
        response.setMessage(HealthCareConstants.PRESCRIPTION_ADDED_SUCCESSFULLY);
        response.setData(prescription);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //patients history
//here i used list because for doctor id there are n patients
    @GetMapping("/{doctorId}/patienthistory")
    public ResponseEntity<ApiResponse<List<Prescription>>> getPatientHistory(
            @PathVariable Long doctorId) {

        if (doctorId == null || doctorId <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(HealthCareConstants.STATUS_FAILURE, HealthCareConstants.INVALID_DOCTOR_ID, null));
        }

        ApiResponse<List<Prescription>> response =
                doctorService.getPatientHistoryByDoctorId(doctorId);
        if (response.getData() == null || response.getData().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HealthCareConstants.STATUS_FAILURE, HealthCareConstants.NO_PATIENT_HISTORY_FOUND, null));
        }
        return ResponseEntity.ok(response);
    }

    //  POST /api/doctors/availability
    @PostMapping("/availability")
    public ResponseEntity<ApiResponse<DoctorAvailability>> setDoctorAvailability(
            @RequestBody DoctorRegistrationRequest request) {

        ApiResponse<DoctorAvailability> response = new ApiResponse<>();

        if (request == null) {
            response.setStatus(HealthCareConstants.FAILED);
            response.setMessage(HealthCareConstants.REQUEST_BODY_IS_MISSING_OR_CONTAINS_INVALID_DATA);
            response.setData(null);
            return ResponseEntity.badRequest().body(response);
        }

        if (request.getDoctorId() == null || request.getDoctorId() <= 0) {
            response.setStatus(HealthCareConstants.FAILED);
            response.setMessage(HealthCareConstants.INVALID_DOCTOR_ID);
            response.setData(null);
            return ResponseEntity.badRequest().body(response);
        }

        DoctorAvailability saved = doctorService.setDoctorAvailability(request);

        response.setStatus(HealthCareConstants.STATUS_SUCCESS);
        response.setMessage(HealthCareConstants.DOCTOR_STATUS_UPDATED_SUCCESSFULLY);
        response.setData(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<?>> updateDoctor(
            @PathVariable Long doctorId,
            @RequestBody Map<String, Object> updates) {
        if (doctorId <= 0) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.INVALID_DOCTOR_ID,
                            null
                    )
            );
        }

        Object updatedDoctor = doctorService.updateDoctor(doctorId, updates);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.DOCTOR_PROFILE_FETCHED_SUCCESSFULLY,
                        updatedDoctor)
        );


    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Doctor>>> searchDoctor(@RequestParam String name) {

        List<Doctor> doctors = doctorService.searchDoctorByName(name);

        if (doctors == null || doctors.isEmpty()) {

            ApiResponse<List<Doctor>> response =
                    new ApiResponse<>(HealthCareConstants.STATUS_FAILURE, HealthCareConstants.DOCTOR_NOT_FOUND, null);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {

            ApiResponse<List<Doctor>> response =
                    new ApiResponse<>(HealthCareConstants.STATUS_SUCCESS, HealthCareConstants.DOCTOR_FETCHED_SUCCESSFULLY, doctors);

            return ResponseEntity.ok(response);
        }
    }


    @GetMapping("/specializations")
    public ResponseEntity<ApiResponse<List<Doctor>>> getDoctorsBySpecialization(
            @RequestParam String specialization) {

        ApiResponse<List<Doctor>> response = doctorService.getDoctorsBySpecialization(specialization);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{doctorId}/reviews")
    public ResponseEntity<ApiResponse<List<Review>>> getDoctorReviews(@PathVariable Long doctorId) {


        if (doctorId == null || doctorId <= 0) {
            ApiResponse<List<Review>> response = new ApiResponse<>();
            response.setStatus(HealthCareConstants.FAILED);
            response.setMessage(HealthCareConstants.DOCTOR_ID_CANNOT_BE_NULL_ZERO_OR_NEGATIVE);
            response.setData(null);
            return ResponseEntity.badRequest().body(response);
        }


        // Fetch reviews from service
        ApiResponse<List<Review>> response = doctorService.getDoctorReviews(doctorId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{doctorId}/availability")
    public ResponseEntity<Map<String, Object>> getDoctorAvailability(
            @PathVariable Long doctorId) {
        List<Map<String, Object>> slots = doctorService.getDoctorAvailability(doctorId);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put(HealthCareConstants.STATUS, HttpStatus.OK.value());
        result.put(HealthCareConstants.MESSAGE, HealthCareConstants.DOCTOR_AVAILABILITY_FETCHED_SUCCESSFULLY);
        result.put(HealthCareConstants.DATA, slots);
        return ResponseEntity.ok(result);
    }

    //  PUT /api/doctors/{doctorId}/status?status=IN_OPERATION
    @PutMapping("/{doctorId}/status")
    public ResponseEntity<ApiResponse<Doctor>> updateDoctorStatus(
            @PathVariable Long doctorId,
            @RequestParam String status) {

        ApiResponse<Doctor> response = new ApiResponse<>();

        if (doctorId <= 0) {
            response.setStatus(HealthCareConstants.FAILED);
            response.setMessage(HealthCareConstants.DOCTOR_ID_CANNOT_BE_NULL_ZERO_OR_NEGATIVE);
            response.setData(null);
            return ResponseEntity.badRequest().body(response);
        }

        if (status == null || status.trim().isEmpty()) {
            response.setStatus(HealthCareConstants.FAILED);
            response.setMessage(HealthCareConstants.STATUS_CANNOT_BE_NULL_OR_EMPTY);
            response.setData(null);
            return ResponseEntity.badRequest().body(response);
        }

        Doctor doctor = doctorService.updateDoctorStatus(doctorId, status);

        response.setStatus(HealthCareConstants.STATUS_SUCCESS);
        response.setMessage(HealthCareConstants.DOCTOR_STATUS_UPDATED_SUCCESSFULLY);
        response.setData(doctor);
        return ResponseEntity.ok(response);
    }


    //  GET /api/doctors/{doctorId}/status
    @GetMapping("/{doctorId}/status")
    public ResponseEntity<ApiResponse<Doctor>> getDoctorStatus(
            @PathVariable Long doctorId) {

        ApiResponse<Doctor> response = new ApiResponse<>();

        if (doctorId <= 0) {
            response.setStatus(HealthCareConstants.FAILED);
            response.setMessage(HealthCareConstants.DOCTOR_ID_CANNOT_BE_NULL_ZERO_OR_NEGATIVE);
            response.setData(null);
            return ResponseEntity.badRequest().body(response);
        }

        Doctor doctor = doctorService.getDoctorStatus(doctorId);

        response.setStatus(HealthCareConstants.STATUS_SUCCESS);
        response.setMessage(HealthCareConstants.DOCTOR_FETCHED_SUCCESSFULLY);
        response.setData(doctor);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<Map<String, Object>> getDoctorsByHospitalId(
            @PathVariable Long hospitalId) {
        List<Map<String, Object>> doctors = doctorService.getDoctorsByHospitalId(hospitalId);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put(HealthCareConstants.STATUS, HttpStatus.OK.value());
        result.put(HealthCareConstants.MESSAGE, HealthCareConstants.DOCTOR_FETCHED_SUCCESSFULLY);
        result.put(HealthCareConstants.DATA, doctors);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<Doctor>> signIn(
            @Valid @RequestBody DoctorSignInRequest request) {
        return ResponseEntity.ok(doctorService.signIn(request));
    }

    @PutMapping("/signout")
    public ResponseEntity<ApiResponse<String>> signOut(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.signOut(doctorId));
    }

    @GetMapping("/patients-seen/{doctorId}/{month}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getPatientsSeen(
            @PathVariable Long doctorId,
            @PathVariable Integer month) {

        ApiResponse<Map<String, Object>> response =
                doctorService.getPatientsSeen(doctorId, month);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/hospitals/{hospitalId}/doctors")
    public ResponseEntity<ApiResponse<List<Doctor>>> getDoctorsByHospital(@PathVariable Long hospitalId) {

        try {

            // Edge Case 1: Invalid hospital ID
            if (hospitalId == null || hospitalId <= 0) {
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>(
                                HealthCareConstants.STATUS_FAILURE,
                                HealthCareConstants.INVALID_HOSPITAL_ID,
                                null
                        )
                );
            }

            // Fetch doctors
            List<Doctor> doctors = doctorService.getDoctorsByHospital(hospitalId);

            // Edge Case 2: No doctors found
            if (doctors == null || doctors.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(
                                HealthCareConstants.STATUS_FAILURE,
                                HealthCareConstants.DOCTOR_NOT_FOUND,
                                null
                        )
                );
            }

            // Success response
            return ResponseEntity.ok(
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_SUCCESS,
                            HealthCareConstants.DOCTOR_FETCHED_SUCCESSFULLY,
                            doctors
                    )
            );

        } catch (Exception e) {

            // Edge Case 3: Unexpected server error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.INTERNAL_SERVER_ERROR,
                            null
                    )
            );
        }
    }

}

