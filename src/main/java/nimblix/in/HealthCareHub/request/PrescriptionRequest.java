package nimblix.in.HealthCareHub.request;

import lombok.Data;

@Data
public class PrescriptionRequest {
    private Long doctorId;
    private Long patientId;
    private String medicineName;
    private String dosage;
    private String instructions;

}

