package nimblix.in.HealthCareHub.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorProfileResponse {

    private Long doctorId;
    private String name;
    private String email;
    private String phone;
    private String qualification;
    private Long experienceYears;

    private Long specializationId;
    private String specializationName;

    private Long hospitalId;
    private String hospitalName;
    private String hospitalAddress;
    private String hospitalCity;
    private String hospitalState;
    private String hospitalPhone;
    private String hospitalEmail;
    private Integer hospitalTotalBeds;

}