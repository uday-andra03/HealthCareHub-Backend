package nimblix.in.HealthCareHub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.model.Room;
import nimblix.in.HealthCareHub.request.HospitalLoginRequest;
import nimblix.in.HealthCareHub.request.HospitalRegistrationRequest;
import nimblix.in.HealthCareHub.request.MedicineAddRequest;
import nimblix.in.HealthCareHub.response.ApiResponse;
import nimblix.in.HealthCareHub.response.RoomResponse;
import nimblix.in.HealthCareHub.service.HospitalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/hospital")
@RequiredArgsConstructor
@Slf4j
public class HospitalController {

    private final HospitalService hospitalService;

    // Register Hospital
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Map<String, Long>>> registerHospital(
            @RequestBody HospitalRegistrationRequest request) {

        Long hospitalId = Long.valueOf(hospitalService.registerHospital(request));

        Map<String, Long> data = new HashMap<>();
        data.put("id", hospitalId);

        ApiResponse<Map<String, Long>> response = ApiResponse.<Map<String, Long>>builder()
                .status(HealthCareConstants.STATUS_SUCCESS)
                .message(HealthCareConstants.REGISTER_SUCCESS)
                .data(data)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Login Hospital Admin
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> loginHospital(
            @RequestBody HospitalLoginRequest request) {

        String token = hospitalService.loginHospital(request);

        Map<String, String> data = new HashMap<>();
        data.put("token", token);

        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
                .status(HealthCareConstants.STATUS_SUCCESS)
                .message("Login successful")
                .data(data)
                .build();

        return ResponseEntity.ok(response);
    }

    // Add Medicine
    @PostMapping("/medicine/add")
    public ResponseEntity<ApiResponse<String>> addMedicine(@RequestBody MedicineAddRequest request) {

        ApiResponse<String> response = new ApiResponse<>();

        if (request == null) {
            response.setStatus(HealthCareConstants.FAILURE);
            response.setMessage("Request body cannot be null");
            return ResponseEntity.badRequest().body(response);
        }

        if (request.getHospitalId() == null) {
            response.setStatus(HealthCareConstants.FAILURE);
            response.setMessage("Hospital Id is required");
            return ResponseEntity.badRequest().body(response);
        }

        if (request.getMedicineName() == null || request.getMedicineName().trim().isEmpty()) {
            response.setStatus(HealthCareConstants.FAILURE);
            response.setMessage("Medicine name is required");
            return ResponseEntity.badRequest().body(response);
        }

        String result = hospitalService.addMedicine(request);

        response.setStatus(HealthCareConstants.STATUS_SUCCESS);
        response.setMessage("Medicine added successfully");
        response.setData(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Add Rooms
    @PostMapping("/{hospitalId}/rooms")
    public ResponseEntity<ApiResponse<String>> addRooms(
            @PathVariable Long hospitalId,
            @RequestBody List<HospitalRegistrationRequest.Room> rooms) {

        hospitalService.addRooms(hospitalId, rooms);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(String.valueOf(HttpStatus.CREATED.value()))
                .message(HealthCareConstants.ROOMS_ADDED_SUCCESSFULLY)
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get Available Rooms
    @GetMapping("/{hospitalId}/available-rooms")
    public ResponseEntity<ApiResponse<List<RoomResponse>>> getAvailableRooms(
            @PathVariable Long hospitalId) {

        List<RoomResponse> rooms = hospitalService.getAvailableRooms(hospitalId);

        ApiResponse<List<RoomResponse>> response = ApiResponse.<List<RoomResponse>>builder()
                .status(String.valueOf(HttpStatus.OK.value()))
                .message(HealthCareConstants.ROOMS_FETCHED_SUCCESSFULLY)
                .data(rooms)
                .build();

        return ResponseEntity.ok(response);
    }

    // Get Hospital by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getHospitalById(
            @PathVariable Long id) {

        ApiResponse<Map<String, Object>> response = new ApiResponse<>();

        try {
            Map<String, Object> hospital = hospitalService.getHospitalById(id);

            response.setStatus(HealthCareConstants.STATUS_SUCCESS);
            response.setMessage(HealthCareConstants.HOSPITAL_FETCHED_SUCCESSFULLY);
            response.setData(hospital);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error fetching hospital with id: {}", id, e);
            throw e;
        }
    }

    // Get Hospital List for Dropdown
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getHospitalList() {

        ApiResponse<Map<String, Object>> response = new ApiResponse<>();

        try {

            Map<String, Object> data = hospitalService.getHospitalList();

            response.setStatus(HealthCareConstants.SUCCESS);
            response.setMessage(HealthCareConstants.HOSPITAL_FETCHED_SUCCESS);
            response.setData(data);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.setStatus(HealthCareConstants.FAILURE);
            response.setMessage(e.getMessage());
            response.setData(null);

            return ResponseEntity.internalServerError().body(response);
        }
    }

    // Weekly Activity
    @GetMapping("/{id}/weekly-activity")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getWeeklyActivity(@PathVariable Long id) {

        ApiResponse<Map<String, Object>> response = new ApiResponse<>();

        try {

            Map<String, Object> data = hospitalService.getWeeklyActivity(id);

            response.setStatus(HealthCareConstants.SUCCESS);
            response.setMessage(HealthCareConstants.WEEKLY_ACTIVITY_FETCHED);
            response.setData(data);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {

            response.setStatus(HealthCareConstants.FAILURE);
            response.setMessage(ex.getMessage());
            response.setData(null);

            return ResponseEntity.internalServerError().body(response);
        }
    }

    // Get All Hospitals
    @GetMapping
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getAllHospitals() {

        ApiResponse<List<Map<String, Object>>> response = hospitalService.getAllHospitals();
        return ResponseEntity.ok(response);
    }

    // Get Hospital Reviews
    @GetMapping("/{hospitalId}/reviews")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getHospitalReviews(@PathVariable Long hospitalId) {

        ApiResponse<List<Map<String, Object>>> response = hospitalService.getHospitalReviews(hospitalId);
        return ResponseEntity.ok(response);
    }

    // Sort Hospitals
    @GetMapping("/sort")
    public ResponseEntity<ApiResponse<List<Hospital>>> sortHospitals(@RequestParam String sortBy) {

        ApiResponse<List<Hospital>> response = new ApiResponse<>();

        try {

            List<Hospital> hospitals = hospitalService.sortHospitals(sortBy);

            response.setStatus(HealthCareConstants.STATUS_SUCCESS);
            response.setMessage(HealthCareConstants.HOSPITAL_SORT_SUCCESS);
            response.setData(hospitals);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.HOSPITAL_SORT_ERROR);
            response.setData(null);

            return ResponseEntity.internalServerError().body(response);
        }
    }

    // Search Hospitals
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Hospital>>> searchHospitals(@RequestParam String name) {

        ApiResponse<List<Hospital>> response = hospitalService.searchHospitalsByName(name);

        if (response.getData() == null || response.getData().isEmpty()) {

            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.HOSPITAL_NOT_FOUND);
            response.setData(new ArrayList<>());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.setStatus(HealthCareConstants.STATUS_SUCCESS);
        response.setMessage("Hospitals fetched successfully");

        return ResponseEntity.ok(response);
    }

    // Search Hospital by Name (Map Response)
    @GetMapping("/searchByName")
    public ApiResponse<List<Hospital>> searchHospitalByName(
            @RequestParam String hospitalName) {

        return hospitalService.searchHospitalsByName(hospitalName);
    }

    // Hospital Statistics
    @GetMapping("/{id}/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getHospitalStats(@PathVariable Long id) {

        ApiResponse<Map<String, Object>> response = new ApiResponse<>();

        try {

            Map<String, Object> stats = hospitalService.getHospitalStats(id);

            response.setStatus(HealthCareConstants.SUCCESS);
            response.setMessage(HealthCareConstants.HOSPITAL_STATS_FETCHED);
            response.setData(stats);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {

            response.setStatus(HealthCareConstants.FAILURE);
            response.setMessage(ex.getMessage());
            response.setData(null);

            return ResponseEntity.internalServerError().body(response);
        }

    }


    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<Map<String,Object>>> filterHospitals(@RequestParam String specialization){

        Map<String,Object> data=hospitalService.filterHospitals(specialization);

        ApiResponse<Map<String,Object>> response=
                new ApiResponse<>(
                        HealthCareConstants.SUCCESS,
                        HealthCareConstants.HOSPITAL_FETCHED_SUCCESS,
                        data);

        return ResponseEntity.ok(response);

    }

}



