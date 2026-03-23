package nimblix.in.HealthCareHub.request;

import lombok.Data;

@Data

public class PatientRegisterRequest {
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String password;
        private String confirmPassword;
        private String gender;

}