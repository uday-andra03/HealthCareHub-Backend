package nimblix.in.HealthCareHub.controller;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.model.Payment;
import nimblix.in.HealthCareHub.response.ApiResponse;
import nimblix.in.HealthCareHub.service.FinanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class FinanceController {

    private final FinanceService financeService;

    @GetMapping("/revenue/daily")
    public ResponseEntity<ApiResponse<Double>> getDailyRevenue() {

        Double revenue = financeService.getDailyRevenue();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.DAILY_REVENUE_FETCHED,
                        revenue
                )
        );
    }


    @GetMapping("/revenue/monthly")
    public ResponseEntity<ApiResponse<Double>> getMonthlyRevenue() {

        Double revenue = financeService.getMonthlyRevenue();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.MONTHLY_REVENUE_FETCHED,
                        revenue
                )
        );
    }


    @GetMapping("/revenue/yearly")
    public ResponseEntity<ApiResponse<Double>> getYearlyRevenue() {

        Double revenue = financeService.getYearlyRevenue();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.YEARLY_REVENUE_FETCHED,
                        revenue
                )
        );
    }


    @GetMapping("/revenue/summary")
    public ResponseEntity<ApiResponse<Map<String, Double>>> getRevenueSummary() {

        Map<String, Double> revenue = financeService.getRevenueSummary();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.REVENUE_SUMMARY_FETCHED,
                        revenue
                )
        );
    }
}

