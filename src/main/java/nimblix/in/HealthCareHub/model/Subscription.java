package nimblix.in.HealthCareHub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
@Data

public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;


    private String planType;


    private Integer durationMonths;


    private LocalDate startDate;


    private LocalDate endDate;


    private String paymentStatus;


    private String createdTime;
    private String updatedTime;

    @PrePersist
    protected void onCreate() {
        createdTime = java.time.LocalDateTime.now().toString();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTime = java.time.LocalDateTime.now().toString();
    }
}

