package nimblix.in.HealthCareHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.model.User;
import nimblix.in.HealthCareHub.repository.UserRepository;
import nimblix.in.HealthCareHub.request.UserRegistrationRequest;
import nimblix.in.HealthCareHub.response.ApiResponse;
import nimblix.in.HealthCareHub.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();

    // ------------------- Register User -------------------
    @Override
    public User registerUser(UserRegistrationRequest request) {

        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        if (userRepository.existsByEmail(request.getEmail().toLowerCase())) {
            throw new RuntimeException("User with this email already exists");
        }

        User user = new User();

        user.setEmail(request.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setEnabled(true);
        user.setPhoneNumber(request.getPhoneNumber());

        return userRepository.save(user);
    }


    // ------------------- Send OTP -------------------
    @Override
    public ApiResponse<User> sendOtp(User user) {

        if ((user.getEmail() == null || user.getEmail().isEmpty()) &&
                (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty())) {
            return new ApiResponse<>("FAILURE", "Email or phone number is required", null);
        }

        User existingUser = null;

        // find user by email
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        }

        // if not found, search by phone
        if (existingUser == null && user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
            existingUser = userRepository.findByPhoneNumber(user.getPhoneNumber()).orElse(null);
        }

        // if still not found -> create new user (for phone OTP case)
        if (existingUser == null) {

            existingUser = new User();

            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                existingUser.setEmail(user.getEmail());
            } else {
                existingUser.setEmail(user.getPhoneNumber() + "@otp.generated");
            }

            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setPassword(passwordEncoder.encode(String.valueOf(random.nextInt(999999))));
            existingUser.setEnabled(true);

            existingUser = userRepository.save(existingUser);
        }

        // generate OTP
        String otp = String.valueOf(100000 + random.nextInt(900000));

        existingUser.setOtp(otp);

        userRepository.save(existingUser);

        System.out.println("Generated OTP for " +
                (existingUser.getEmail() != null ? existingUser.getEmail() : existingUser.getPhoneNumber())
                + " : " + otp);

        return new ApiResponse<>("SUCCESS", "OTP sent successfully", existingUser);
    }


    // ------------------- Verify OTP -------------------
    @Override
    public ApiResponse<Void> verifyOtp(User user, String otp) {

        if ((user.getEmail() == null || user.getEmail().isEmpty()) &&
                (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty())) {
            return new ApiResponse<>("FAILURE", "Email or phone number is required", null);
        }

        if (otp == null || otp.isEmpty()) {
            return new ApiResponse<>("FAILURE", "OTP is required", null);
        }

        User existingUser = null;

        // find by email
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        }

        // if not found search by phone
        if (existingUser == null && user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
            existingUser = userRepository.findByPhoneNumber(user.getPhoneNumber()).orElse(null);
        }

        if (existingUser == null) {
            return new ApiResponse<>("FAILURE", "User not found", null);
        }

        if (existingUser.getOtp() == null) {
            return new ApiResponse<>("FAILURE", "OTP not generated", null);
        }

        if (!existingUser.getOtp().equals(otp)) {
            return new ApiResponse<>("FAILURE", "Invalid OTP", null);
        }

        // OTP correct → clear OTP
        existingUser.setOtp(null);
        userRepository.save(existingUser);

        return new ApiResponse<>("SUCCESS", "OTP verified successfully", null);
    }
}