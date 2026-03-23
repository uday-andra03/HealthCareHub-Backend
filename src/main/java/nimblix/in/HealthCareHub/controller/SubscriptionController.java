package nimblix.in.HealthCareHub.controller;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.response.ApiResponse;
import nimblix.in.HealthCareHub.service.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getHospitalsWithSubscriptions() {

        List<Map<String, Object>> hospitals = subscriptionService.getHospitalsWithSubscriptions();

        if (hospitals == null || hospitals.isEmpty()) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.NO_ACTIVE_HOSPITAL_SUBSCRIPTIONS,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("hospitals", hospitals);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.HOSPITALS_WITH_SUBSCRIPTIONS_FETCHED_SUCCESS,
                        data
                );

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{hospitalId}/renew")
    public ResponseEntity<ApiResponse<Map<String, Object>>> renewSubscription(
            @PathVariable Long hospitalId) {

        if (hospitalId == null || hospitalId <= 0) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.INVALID_HOSPITAL_ID,
                            null
                    );

            return ResponseEntity.badRequest().body(response);
        }

        Map<String, Object> subscription =
                subscriptionService.renewSubscription(hospitalId);

        if (subscription == null) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.SUBSCRIPTION_NOT_FOUND_FOR_HOSPITAL,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("subscription", subscription);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.SUBSCRIPTION_RENEWED_SUCCESS,
                        data
                );

        return ResponseEntity.ok(response);
    }


    @PostMapping("/payment")
    public ResponseEntity<ApiResponse<Map<String, Object>>> makeSubscriptionPayment(
            @RequestBody Map<String, Object> request) {

        if (request == null ||
                request.get("hospitalId") == null ||
                request.get("amount") == null) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.INVALID_PAYMENT_REQUEST,
                            null
                    );

            return ResponseEntity.badRequest().body(response);
        }

        Map<String, Object> payment =
                subscriptionService.makeSubscriptionPayment(request);

        if (payment == null) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.SUBSCRIPTION_PAYMENT_FAILED,
                            null
                    );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("payment", payment);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.SUBSCRIPTION_PAYMENT_SUCCESS,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{subscriptionId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateSubscriptionPlan(
            @PathVariable Long subscriptionId,
            @RequestBody Map<String, Object> request) {

        Map<String, Object> subscription =
                subscriptionService.updateSubscriptionPlan(subscriptionId, request);

        if (subscription == null) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.SUBSCRIPTION_NOT_FOUND,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("subscription", subscription);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.SUBSCRIPTION_UPDATED_SUCCESSFULLY,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{subscriptionId}/upgrade")
    public ResponseEntity<ApiResponse<Map<String, Object>>> upgradeSubscription(
            @PathVariable Long subscriptionId,
            @RequestBody Map<String, Object> request) {

        Map<String, Object> subscription =
                subscriptionService.upgradeSubscription(subscriptionId, request);

        if (subscription == null) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.SUBSCRIPTION_NOT_FOUND,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put(HealthCareConstants.SUBSCRIPTION, subscription);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.SUBSCRIPTION_UPGRADED_SUCCESSFULLY,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{subscriptionId}/downgrade")
    public ResponseEntity<ApiResponse<Map<String, Object>>> downgradeSubscription(
            @PathVariable Long subscriptionId,
            @RequestBody Map<String, Object> request) {

        Map<String, Object> subscription =
                subscriptionService.downgradeSubscription(subscriptionId, request);

        if (subscription == null) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.SUBSCRIPTION_NOT_FOUND,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put(HealthCareConstants.SUBSCRIPTION, subscription);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.SUBSCRIPTION_DOWNGRADED_SUCCESSFULLY,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{subscriptionId}/extend")
    public ResponseEntity<ApiResponse<Map<String, Object>>> extendSubscription(
            @PathVariable Long subscriptionId,
            @RequestBody Map<String, Object> request) {

        Map<String, Object> subscription =
                subscriptionService.extendSubscription(subscriptionId, request);

        if (subscription == null) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.SUBSCRIPTION_NOT_FOUND,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put(HealthCareConstants.SUBSCRIPTION, subscription);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.SUBSCRIPTION_EXTENDED_SUCCESSFULLY,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{hospitalId}/history")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSubscriptionHistory(
            @PathVariable Long hospitalId) {

        List<Map<String, Object>> subscriptions =
                subscriptionService.getSubscriptionHistory(hospitalId);

        if (subscriptions == null || subscriptions.isEmpty()) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.SUBSCRIPTION_HISTORY_NOT_FOUND,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("subscriptions", subscriptions);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.SUBSCRIPTION_HISTORY_FETCHED_SUCCESSFULLY,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/subscriptions/{hospitalId}/status")
    public ResponseEntity<ApiResponse<String>> checkSubscriptionStatus(
            @PathVariable Long hospitalId) {

        if (hospitalId == null || hospitalId <= 0) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.INVALID_HOSPITAL_ID,
                            null
                    )
            );
        }

        String status = subscriptionService.checkSubscriptionStatus(hospitalId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.SUBSCRIPTION_STATUS_FETCHED,
                        status
                )
        );
    }
}

