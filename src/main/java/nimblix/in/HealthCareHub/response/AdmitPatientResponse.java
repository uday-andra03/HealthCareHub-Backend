package nimblix.in.HealthCareHub.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmitPatientResponse {

    private Long admissionId;

    // Patient info
    private Long patientId;
    private String patientName;
    private String patientPhone;

    // Doctor info
    private Long doctorId;
    private String doctorName;
    private String doctorSpecialization;

    // Room info
    private Long roomId;
    private String roomNumber;
    private String roomType;

    // Admission details
    private String admissionDate;
    private String admissionReason;
    private String symptoms;
    private String initialDiagnosis;
    private String status;
}