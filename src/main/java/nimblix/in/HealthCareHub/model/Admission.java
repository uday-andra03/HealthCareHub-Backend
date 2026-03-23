package nimblix.in.HealthCareHub.model;

import jakarta.persistence.*;
import lombok.*;
import nimblix.in.HealthCareHub.utility.HealthCareUtil;

@Entity
@Table(name = "admissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admissionId;

    // Simple Long FK - no @ManyToOne mapping
    @Column(name = "patientId")
    private Long patientId;

    // Simple Long FK - no @ManyToOne mapping
    @Column(name = "doctor_id")
    private Long doctorId;

    // Simple Long FK - no @ManyToOne mapping
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "admission_date")
    private String admissionDate;

    @Column(name = "admission_reason")
    private String admissionReason;

    private String symptoms;

    private String initialDiagnosis;

    private String status;  // "ADMITTED", "DISCHARGED", "TRANSFERRED"

    @Column(name = "created_time")
    private String createdTime;

    @Column(name = "updated_time")
    private String updatedTime;

    @PrePersist
    protected void onCreate() {
        this.createdTime = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
        this.updatedTime = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
        if (this.admissionDate == null) {
            this.admissionDate = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
        }

    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedTime = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
    }
}