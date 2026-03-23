package nimblix.in.HealthCareHub.controller;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.exception.UserNotFoundException;
import nimblix.in.HealthCareHub.model.User;
import nimblix.in.HealthCareHub.request.UserRegistrationRequest;
import nimblix.in.HealthCareHub.response.ApiResponse;
import nimblix.in.HealthCareHub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // ------------------- Register User -------------------
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody UserRegistrationRequest request) {

        try {

            User user = userService.registerUser(request);

            ApiResponse<User> response =
                    new ApiResponse<>("SUCCESS", "User registered successfully", user);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (UserNotFoundException e) {

            ApiResponse<User> response =
                    new ApiResponse<>("FAILURE", e.getMessage(), null);

            return new ResponseEntity<>(response, HttpStatus.CONFLICT);

        } catch (Exception e) {

            ApiResponse<User> response =
                    new ApiResponse<>("FAILURE", "Registration failed: " + e.getMessage(), null);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    // ------------------- Send OTP -------------------
    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse<User>> sendOtp(@RequestBody User user) {

        ApiResponse<User> response = userService.sendOtp(user);

        if ("FAILURE".equals(response.getStatus())) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // ------------------- Verify OTP -------------------
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<Void>> verifyOtp(
            @RequestBody User user,
            @RequestParam String otp) {

        ApiResponse<Void> response = userService.verifyOtp(user, otp);

        if ("FAILURE".equals(response.getStatus())) {

            if ("User not found".equals(response.getMessage())) {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}