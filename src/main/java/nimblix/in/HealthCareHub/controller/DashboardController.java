package nimblix.in.HealthCareHub.controller;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.exception.ResourceNotFoundException;
import nimblix.in.HealthCareHub.response.*;
import nimblix.in.HealthCareHub.service.DashboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    //Retrieve overall dashboard statistics
    //such as total beds, active doctors, patients served, and average rating.

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboardSummary() {

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>();

        try {

            Map<String, Object> summary =
                    dashboardService.getDashboardSummary();

            response.setStatus(HealthCareConstants.STATUS_SUCCESS);
            response.setMessage(HealthCareConstants.DASHBOARD_FETCHED_SUCCESS);
            response.setData(summary);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {

            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(HealthCareConstants.SOMETHING_WENT_WRONG);
            response.setData(null);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);

        }

    }

    // Retrieve hospital admissions and discharges activity for the last 14 days

    // Get Admissions & Discharges Activity
    @GetMapping("/admissions-discharges")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAdmissionsDischarges() {

        ApiResponse<Map<String, Object>> response = new ApiResponse<>();

        try {

            Map<String, Object> data = dashboardService.getAdmissionsDischargesActivity();

            response.setStatus(HealthCareConstants.STATUS_SUCCESS);
            response.setMessage(HealthCareConstants.ACTIVITY_FETCHED_SUCCESS);
            response.setData(data);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {

            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(ex.getMessage());
            response.setData(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Retrieve distribution of hospital specializations for dashboard pie chart
    @GetMapping("/specializations")
    public ResponseEntity<ApiResponse<Map<String, Object>>>
    getSpecializationsDistribution() {

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>();

        try {

            Map<String, Object> data =
                    dashboardService.getSpecializationsDistribution();

            response.setStatus(HealthCareConstants.STATUS_SUCCESS);
            response.setMessage(HealthCareConstants.SPECIALIZATION_FETCHED_SUCCESS);
            response.setData(data);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (RuntimeException ex) {
            response.setStatus(HealthCareConstants.STATUS_FAILURE);

            response.setMessage(ex.getMessage());
            response.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception ex) {
            response.setStatus(HealthCareConstants.STATUS_FAILURE);

            response.setMessage(HealthCareConstants.INTERNAL_SERVER_ERROR);

            response.setData(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Retrieve surgeries and emergency cases activity for dashboard bar chart
    @GetMapping("/surgeries-emergencies")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSurgeriesEmergenciesActivity() {

        ApiResponse<Map<String, Object>> response = new ApiResponse<>();

        try {

            Map<String, Object> data = dashboardService.getSurgeriesEmergenciesActivity();

            response.setStatus(HealthCareConstants.STATUS_SUCCESS);
            response.setMessage(HealthCareConstants.ACTIVITY_FETCHED_SUCCESS);
            response.setData(data);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {

            response.setStatus(HealthCareConstants.STATUS_FAILURE);
            response.setMessage(ex.getMessage());
            response.setData(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Hospital overview (Dashboard table)
    @GetMapping("/hospital-overview")
    public ResponseEntity<ApiResponse<Map<String, Object>>>
    getHospitalOverview() {

        ApiResponse<Map<String, Object>> response = new ApiResponse<>();

        try {
            Map<String, Object> data = dashboardService.getHospitalOverview();
            response.setStatus(HealthCareConstants.STATUS_SUCCESS);

            response.setMessage(HealthCareConstants.HOSPITAL_OVERVIEW_FETCHED_SUCCESS);

            response.setData(data);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (RuntimeException ex) {
            response.setStatus(HealthCareConstants.STATUS_FAILURE);

            response.setMessage(ex.getMessage());

            response.setData(null);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception ex) {

            response.setStatus(HealthCareConstants.STATUS_FAILURE);

            response.setMessage(HealthCareConstants.INTERNAL_SERVER_ERROR);

            response.setData(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
