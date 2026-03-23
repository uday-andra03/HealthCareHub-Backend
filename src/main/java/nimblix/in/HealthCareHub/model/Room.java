package nimblix.in.HealthCareHub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nimblix.in.HealthCareHub.utility.HealthCareUtil;

import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(nullable = false, unique = true)
    private String roomNumber;

   @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;


    private Integer floorNumber;

    private Integer capacity;

    @Column(nullable = false)
    private Double pricePerDay;

    private Boolean hasAC;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status;

    private String createdTime;
    private String updatedTime;

    @PrePersist
    protected void onCreate() {
        this.createdTime = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();;
        this.updatedTime = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();;
        if (this.status == null) {
            this.status = RoomStatus.AVAILABLE;
        }
        if (this.hasAC == null) {
            this.hasAC = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedTime = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();;
    }

    // Used by AdmissionServiceImpl — room.getRoomType()
    public String getRoomType() {
        return this.roomType != null ? this.roomType.name() : null;
    }

    public enum RoomType {
        GENERAL, PRIVATE, SEMI_PRIVATE, ICU, EMERGENCY
    }

    public enum RoomStatus {
        AVAILABLE, OCCUPIED, MAINTENANCE, RESERVED
    }
}