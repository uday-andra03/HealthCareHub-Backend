package nimblix.in.HealthCareHub.request;

import lombok.Getter;
import lombok.Setter;
import nimblix.in.HealthCareHub.model.Role;

@Getter
@Setter
public class UserRegistrationRequest {
    private String email;
    private String phoneNumber;
    private String password;
    private Role role;
    private String otp;   // This is used for OTP verification
}