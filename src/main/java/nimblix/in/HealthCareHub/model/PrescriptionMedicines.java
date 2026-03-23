package nimblix.in.HealthCareHub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionMedicines {

    @Id
    @Column(name = "PrescriptionMedicine_id")
    private String id;

    @Column(name = "Medicine_Name")
    private String medicineName;

    @Column(name = "Dosage")
    private String dosage;

    @ManyToOne
    @JoinColumn(name = "Prescription_id")
    private Prescription prescriptionId;
}
