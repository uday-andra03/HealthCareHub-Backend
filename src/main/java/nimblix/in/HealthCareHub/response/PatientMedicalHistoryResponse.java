package nimblix.in.HealthCareHub.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//   Mimanshu
public class PatientMedicalHistoryResponse {

    private Long patientId;
    private String patientName;
    private Integer age;
    private String gender;
    private String disease;

    private String doctorName;
    private String hospitalName;
    private Long hospitalId;

    private String patientEmail;
    private String doctorEmail;

    // Lab Result Details
    private String testName;
    private String testResult;

    // Medicine Details
    private String medicineName;

    // Prescription Details
    private String prescription;

}
