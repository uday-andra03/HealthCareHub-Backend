package nimblix.in.HealthCareHub.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRegistrationRequest {

    private Long doctorId;

    @NotBlank(message = "Doctor name must not be blank")
    private String doctorName;

    @NotBlank(message = "Email must not be blank")
    private String doctorEmail;

    @NotBlank(message = "Password must not be blank")
    private String password;

    private String phoneNo;

    private String qualification;

    private String description;

    private Long experience;

    private Double consultationFee;

    @NotNull(message = "Hospital ID must not be null")
    private Long hospitalId;

    private String specializationName;

    @NotNull(message = "Specialization ID must not be null")
    private Long specializationId;

    // Doctor availability fields
    private String availableDate;   // format: dd-MM-yyyy
    private String startTime;       // format: HH:mm
    private String endTime;         // format: HH:mm
    private boolean isAvailable;

    private String doctorStatus;
}