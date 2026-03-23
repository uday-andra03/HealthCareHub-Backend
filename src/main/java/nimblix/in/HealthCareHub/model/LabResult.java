package nimblix.in.HealthCareHub.model;

import jakarta.persistence.*;
import lombok.*;
import nimblix.in.HealthCareHub.utility.HealthCareUtil;

@Entity
@Table(name = "lab_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    // Simple Long FK - no @ManyToOne mapping
    @Column(name = "patient_id")
    private Long patientId;

    // Simple Long FK - no @ManyToOne mapping
    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(nullable = false)
    private String testName;

    private String testCategory;

    private String result;

    private String referenceRange;

    private String unit;

    @Column(nullable = false)
    private String status;  // "PENDING", "COMPLETED", "NORMAL", "ABNORMAL"

    private String remarks;

    @Column(name = "tested_at")
    private String testedAt;

    @Column(name = "created_time")
    private String createdTime;

    @Column(name = "updated_time")
    private String updatedTime;

    @PrePersist
    protected void onCreate() {
        this.createdTime = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
        this.updatedTime = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
        if (this.testedAt == null) {
            this.testedAt = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
        }
        if (this.status == null) {
            this.status = "PENDING";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedTime = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
    }
}