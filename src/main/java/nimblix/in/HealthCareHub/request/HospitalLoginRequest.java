package nimblix.in.HealthCareHub.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalLoginRequest {

    private String email;
    private String password;
}
