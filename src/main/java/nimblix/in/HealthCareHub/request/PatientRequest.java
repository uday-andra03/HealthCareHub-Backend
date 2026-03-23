package nimblix.in.HealthCareHub.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PatientRequest {
    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp="\\d{10}", message="Phone must be 10 digits")
    private String phone;

    @NotBlank(message = "Password is required")
    private String password;

    // Getters and Setters
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
