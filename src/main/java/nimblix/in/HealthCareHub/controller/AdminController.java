package nimblix.in.HealthCareHub.controller;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.model.Payment;
import nimblix.in.HealthCareHub.model.Subscription;
import nimblix.in.HealthCareHub.response.ApiResponse;
import nimblix.in.HealthCareHub.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final FinanceService financeService;
    private final HospitalService hospitalService;
    private final DashboardService dashboardService;
    private final SubscriptionService subscriptionService;
    private final AdminService adminService;


    //Retrieve all system users
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<Map<String,Object>>>
    getAllUsers(){

        Map<String,Object> users = adminService.getAllUsers();

        ApiResponse<Map<String,Object>> response = new ApiResponse<>(
                HealthCareConstants.SUCCESS,
                HealthCareConstants.USERS_FETCHED_SUCCESS,
                users
        );
        return ResponseEntity.ok(response);
    }

    //Retrieve user details by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Map<String,Object>>>
    getUserById(@PathVariable Long id){

        Map<String,Object> user = adminService.getUserById(id);

        ApiResponse<Map<String,Object>> response = new ApiResponse<>(
                HealthCareConstants.SUCCESS,
                HealthCareConstants.USER_FETCHED_SUCCESS,
                user
        );
        return ResponseEntity.ok(response);
    }

    //Approve doctor registration
    @PutMapping("/doctors/{id}/approve")
    public ResponseEntity<ApiResponse<Map<String,Object>>>approveDoctor(@PathVariable Long id){

        Map<String,Object> data=adminService.approveDoctor(id);
        ApiResponse<Map<String,Object>> response= new ApiResponse<>(
                HealthCareConstants.SUCCESS,
                HealthCareConstants.DOCTOR_APPROVED,
                data);

        return ResponseEntity.ok(response);

    }

    //Reject doctor registration
    @PutMapping("/doctors/{id}/reject")
    public ResponseEntity<ApiResponse<Map<String,Object>>>rejectDoctor(@PathVariable Long id){

        Map<String,Object> data=adminService.rejectDoctor(id);

        ApiResponse<Map<String,Object>> response=
                new ApiResponse<>(
                        HealthCareConstants.SUCCESS,
                        HealthCareConstants.DOCTOR_REJECTED,
                        data);

        return ResponseEntity.ok(response);

    }

    //Remove doctor from system

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<ApiResponse<Map<String,Object>>>deleteDoctor(@PathVariable Long id){

        Map<String,Object> data=adminService.deleteDoctor(id);
        ApiResponse<Map<String,Object>> response=
                new ApiResponse<>
                        (HealthCareConstants.SUCCESS,
                                HealthCareConstants.DOCTOR_DELETED,
                                data);

        return ResponseEntity.ok(response);

    }

    //Retrieve all hospitals
    @GetMapping("/hospitals")
    public ResponseEntity<ApiResponse<Map<String,Object>>>getAllHospitals(){

        Map<String,Object> data=adminService.getAllHospitals();

        ApiResponse<Map<String,Object>> response=
                new ApiResponse<>(
                        HealthCareConstants.SUCCESS,
                        HealthCareConstants.HOSPITAL_FETCHED_SUCCESS,
                        data);

        return ResponseEntity.ok(response);

    }

    //Retrieve hospital details
    @GetMapping("/hospitals/{id}")
    public ResponseEntity<ApiResponse<Map<String,Object>>>getHospitalById(@PathVariable Long id){

        Map<String,Object> data=adminService.getHospitalById(id);

        ApiResponse<Map<String,Object>> response=
                new ApiResponse<>(
                        HealthCareConstants.SUCCESS,
                        HealthCareConstants.HOSPITAL_FETCHED_SUCCESS,
                        data);

        return ResponseEntity.ok(response);

    }

    //Approve hospital registration
    @PutMapping("/hospitals/{id}/approve")
    public ResponseEntity<ApiResponse<Map<String,Object>>>approveHospital(@PathVariable Long id){

        Map<String,Object> data=adminService.approveHospital(id);

        ApiResponse<Map<String,Object>> response=
                new ApiResponse<>(
                        HealthCareConstants.SUCCESS,
                        HealthCareConstants.HOSPITAL_APPROVED,
                        data);

        return ResponseEntity.ok(response);

    }

    //Remove hospital (soft delete recommended)

    @DeleteMapping("/hospitals/{id}")
    public ResponseEntity<ApiResponse<Map<String,Object>>>deleteHospital(@PathVariable Long id){

        Map<String,Object> data=adminService.deleteHospital(id);

        ApiResponse<Map<String,Object>> response=
                new ApiResponse<>(
                        HealthCareConstants.SUCCESS,
                        HealthCareConstants.HOSPITAL_DELETED,
                        data);

        return ResponseEntity.ok(response);

    }

    //Retrieve all patients

    @GetMapping("/patients")
    public ResponseEntity<ApiResponse<Map<String,Object>>>getAllPatients(){

        Map<String,Object> data=adminService.getAllPatients();

        ApiResponse<Map<String,Object>> response=
                new ApiResponse<>(
                        HealthCareConstants.SUCCESS,
                        HealthCareConstants.PATIENTS_FETCHED,
                        data);

        return ResponseEntity.ok(response);

    }

    //Retrieve patient details
    @GetMapping("/patients/{id}")
    public ResponseEntity<ApiResponse<Map<String,Object>>>getPatientById(@PathVariable Long id){

        Map<String,Object> data=adminService.getPatientById(id);

        ApiResponse<Map<String,Object>> response=
                new ApiResponse<>(
                        HealthCareConstants.SUCCESS,
                        HealthCareConstants.PATIENT_FETCHED,
                        data);

        return ResponseEntity.ok(response);

    }

    //Remove patient

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<ApiResponse<Map<String,Object>>>deletePatient(@PathVariable Long id){

        Map<String,Object> data=adminService.deletePatient(id);

        ApiResponse<Map<String,Object>> response=
                new ApiResponse<>(
                        HealthCareConstants.SUCCESS,
                        HealthCareConstants.PATIENT_DELETED,
                        data);

        return ResponseEntity.ok(response);

    }

    //Add new specialization
    @PostMapping("/specialization")
    public ResponseEntity<ApiResponse<Map<String,Object>>>addSpecialization(@RequestBody Map<String,Object> request){

        Map<String,Object> data=adminService.addSpecialization(request);

        ApiResponse<Map<String,Object>> response=
                new ApiResponse<>(
                        HealthCareConstants.SUCCESS,
                        HealthCareConstants.SPECIALIZATION_ADDED,
                        data);

        return ResponseEntity.ok(response);

    }

    //Retrieve all specializations
    @GetMapping("/specializations")
    public ResponseEntity<ApiResponse<Map<String,Object>>>getAllSpecializations(){

        Map<String,Object> data=adminService.getAllSpecializations();

        ApiResponse<Map<String,Object>> response=
                new ApiResponse<>(
                        HealthCareConstants.SUCCESS,
                        HealthCareConstants.SPECIALIZATIONS_FETCHED,
                        data);

        return ResponseEntity.ok(response);

    }


    //Remove specialization
    @DeleteMapping("/specialization/{id}")
    public ResponseEntity<ApiResponse<Map<String,Object>>>deleteSpecialization(@PathVariable Long id){

        Map<String,Object> data=adminService.deleteSpecialization(id);

        ApiResponse<Map<String,Object>> response=
                new ApiResponse<>(
                        HealthCareConstants.SUCCESS,
                        HealthCareConstants.SPECIALIZATION_DELETED,
                        data);

        return ResponseEntity.ok(response);

    }


    @GetMapping("/payments")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getPaymentHistory() {

        List<Payment> payments = financeService.getAllPayments();

        if (payments == null || payments.isEmpty()) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.PAYMENT_HISTORY_NOT_FOUND,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("payments", payments);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.PAYMENT_HISTORY_FETCHED_SUCCESS,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/hospitals/{id}/credentials")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getHospitalCredentials(@PathVariable Long id) {

        if (id == null) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.INVALID_HOSPITAL_ID,
                            null
                    );

            return ResponseEntity.badRequest().body(response);
        }

        Map<String, Object> credentials = hospitalService.getHospitalCredentials(id);

        if (credentials == null || credentials.isEmpty()) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.HOSPITAL_NOT_FOUND,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put(HealthCareConstants.HOSPITAL_CREDENTIALS, credentials);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.HOSPITAL_CREDENTIALS_FETCHED_SUCCESS,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/notifications")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getNotifications() {

        List<Map<String, Object>> notifications = dashboardService.getNotifications();

        if (notifications == null || notifications.isEmpty()) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.NOTIFICATIONS_NOT_FOUND,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put(HealthCareConstants.NOTIFICATIONS, notifications);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.NOTIFICATIONS_FETCHED_SUCCESS,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/notifications")
    public ResponseEntity<ApiResponse<Map<String, Object>>> sendNotification(
            @RequestBody Map<String, String> request) {

        String title = request.get("title");
        String message = request.get("message");

        if (title == null || title.trim().isEmpty() ||
                message == null || message.trim().isEmpty()) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.INVALID_NOTIFICATION_REQUEST,
                            null
                    );

            return ResponseEntity.badRequest().body(response);
        }

        Map<String, Object> notification =
                dashboardService.sendNotification(title, message);

        Map<String, Object> data = new HashMap<>();
        data.put(HealthCareConstants.NOTIFICATION, notification);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.NOTIFICATION_SENT_SUCCESSFULLY,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/hospitals/{id}/reset-password")
    public ResponseEntity<ApiResponse<Map<String, Object>>> resetHospitalPassword(
            @PathVariable Long id,
            @RequestParam String newPassword) {

        if (id == null || newPassword == null || newPassword.trim().isEmpty()) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.INVALID_PASSWORD_REQUEST,
                            null
                    );

            return ResponseEntity.badRequest().body(response);
        }

        boolean updated = hospitalService.resetHospitalPassword(id, newPassword);

        if (!updated) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.HOSPITAL_NOT_FOUND,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("hospitalId", id);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.HOSPITAL_PASSWORD_RESET_SUCCESS,
                        data
                );

        return ResponseEntity.ok(response);
    }

    //Rooms

    @PostMapping("/rooms")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createRoom(@RequestBody Map<String, Object> request) {

        Long hospitalId = Long.valueOf(request.get("hospitalId").toString());
        String roomNumber = (String) request.get("roomNumber");
        String roomType = (String) request.get("roomType");

        if (hospitalId == null || roomNumber == null || roomType == null) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.INVALID_ROOM_REQUEST,
                            null
                    );

            return ResponseEntity.badRequest().body(response);
        }

        Map<String, Object> room = hospitalService.createRoom(hospitalId, roomNumber, roomType);

        Map<String, Object> data = new HashMap<>();
        data.put(HealthCareConstants.ROOM, room);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.ROOM_CREATED_SUCCESS,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/rooms")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllRooms() {

        List<Map<String, Object>> rooms = hospitalService.getAllRooms();

        if (rooms == null || rooms.isEmpty()) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.ROOMS_NOT_FOUND,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put(HealthCareConstants.ROOMS, rooms);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.ROOMS_FETCHED_SUCCESSFULLY,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/rooms/{roomNumber}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getRoomById(@PathVariable String roomNumber) {

        Map<String, Object> room = hospitalService.getRoomByNumber(roomNumber);

        if (room == null) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.ROOM_NOT_FOUND,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put(HealthCareConstants.ROOM, room);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.ROOM_FETCHED_SUCCESSFULLY,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/rooms/{roomNumber}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateRoom(
            @PathVariable String roomNumber,
            @RequestBody Map<String, Object> request) {

        String roomType = (String) request.get("roomType");
        Boolean available = (Boolean) request.get("available");

        Map<String, Object> room = hospitalService.updateRoom(roomNumber, roomType, available);

        if (room == null) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.ROOM_NOT_FOUND,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put(HealthCareConstants.ROOM, room);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.ROOM_UPDATED_SUCCESSFULLY,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/rooms/{roomNumber}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> deleteRoom(@PathVariable String roomNumber) {

        boolean deleted = hospitalService.deleteRoom(roomNumber);

        if (!deleted) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.ROOM_NOT_FOUND,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("roomNumber", roomNumber);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.ROOM_DELETED_SUCCESSFULLY,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/rooms/hospital/{hospitalId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getRoomsByHospital(@PathVariable Long hospitalId) {

        List<Map<String, Object>> rooms = hospitalService.getRoomsByHospital(hospitalId);

        if (rooms == null || rooms.isEmpty()) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.ROOMS_NOT_FOUND,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put(HealthCareConstants.ROOMS, rooms);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.ROOMS_FETCHED_SUCCESSFULLY,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/rooms/{roomNumber}/status")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateRoomStatus(
            @PathVariable String roomNumber,
            @RequestBody Map<String, Object> request) {

        Boolean available = (Boolean) request.get("available");

        if (available == null) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.INVALID_ROOM_STATUS_REQUEST,
                            null
                    );

            return ResponseEntity.badRequest().body(response);
        }

        Map<String, Object> room = hospitalService.updateRoomStatus(roomNumber, available);

        if (room == null) {

            ApiResponse<Map<String, Object>> response =
                    new ApiResponse<>(
                            HealthCareConstants.STATUS_FAILURE,
                            HealthCareConstants.ROOM_NOT_FOUND,
                            null
                    );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Map<String, Object> data = new HashMap<>();
        data.put(HealthCareConstants.ROOM, room);

        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.ROOM_STATUS_UPDATED_SUCCESSFULLY,
                        data
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/subscriptions/expiring-soon")
    public ResponseEntity<ApiResponse<List<Subscription>>> getExpiringSubscriptions(
            @RequestParam(defaultValue = "7") int days) {

        List<Subscription> subscriptions =
                subscriptionService.getExpiringSubscriptions(days);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.SUBSCRIPTIONS_EXPIRING_SOON,
                        subscriptions
                )
        );
    }

    @PostMapping("/subscriptions/{hospitalId}/suspend")
    public ResponseEntity<ApiResponse<String>> suspendSubscription(
            @PathVariable Long hospitalId) {

        subscriptionService.suspendSubscription(hospitalId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.SUBSCRIPTION_SUSPENDED,
                        null
                )
        );
    }

    @PatchMapping("/subscriptions/{subscriptionId}/reactivate")
    public ResponseEntity<ApiResponse<String>> reactivateSubscription(
            @PathVariable Long subscriptionId) {

        subscriptionService.reactivateSubscription(subscriptionId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.SUBSCRIPTION_REACTIVATED,
                        null
                )
        );
    }

    @PostMapping("/subscriptions/{hospitalId}/reminder")
    public ResponseEntity<ApiResponse<String>> sendSubscriptionReminder(
            @PathVariable Long hospitalId) {

        subscriptionService.sendSubscriptionReminder(hospitalId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HealthCareConstants.STATUS_SUCCESS,
                        HealthCareConstants.SUBSCRIPTION_REMINDER_SENT,
                        null
                )
        );
    }
}

