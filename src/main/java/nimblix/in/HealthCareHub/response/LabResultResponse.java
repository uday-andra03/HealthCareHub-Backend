package nimblix.in.HealthCareHub.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabResultResponse {

    private Long resultId;

    // Patient info
    private Long patientId;
    private String patientName;
    private String patientPhone;

    // Doctor info
    private Long doctorId;
    private String doctorName;
    private String doctorSpecialization;

    // Test details
    private String testName;
    private String testCategory;
    private String result;
    private String referenceRange;
    private String unit;
    private String status;              // "PENDING", "COMPLETED", "NORMAL", "ABNORMAL"
    private String remarks;
    private String testedAt;            // IST String format "dd-MM-yyyy HH:mm"
}