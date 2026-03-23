package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.model.User;
import nimblix.in.HealthCareHub.request.UserRegistrationRequest;
import nimblix.in.HealthCareHub.response.ApiResponse;

public interface UserService {

    User registerUser(UserRegistrationRequest request);

    ApiResponse<User> sendOtp(User user);

    ApiResponse<Void> verifyOtp(User user, String otp);}
