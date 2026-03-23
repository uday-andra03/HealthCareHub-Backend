package nimblix.in.HealthCareHub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "prescription")
@Data
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicineName;
    private String dosage;
    private String instructions;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;


    //as i m getting whole doctor table and patient table details
    //so which are required that much only allowed using json property
    @JsonProperty("doctorId")
    public Long getDoctorId() {
        return doctor != null ? doctor.getId() : null;
    }

    @JsonProperty("doctorName")
    public String getDoctorName() {
        return doctor != null ? doctor.getName() : null;
    }

    @JsonProperty("patientId")
    public Long getPatientId() {
        return patient != null ? patient.getId() : null;
    }

    @JsonProperty("patientName")
    public String getPatientName() {
        return patient != null ? patient.getName() : null;
    }
}


