package nimblix.in.HealthCareHub.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmitPatientRequest {

    private Long patientId;
    private Long doctorId;
    private Long roomId;


    private String admissionReason;
    private String symptoms;
    private String initialDiagnosis;
}
