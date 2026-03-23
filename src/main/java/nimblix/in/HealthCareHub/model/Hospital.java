package nimblix.in.HealthCareHub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import nimblix.in.HealthCareHub.utility.HealthCareUtil;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hospitals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    //    Raksha
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews;

    private String address;

    private String city;

    private String state;

    private String phone;

    private String email;

    private Integer totalBeds;

    private String country;

    private Integer establishedYear;

    @Column(name="specialization")
    private String specialization;


    private String location;


    @ElementCollection
    private List<String> specializations;   // ✅ Add this field

    @ElementCollection
    private List<String> doctors;           // ✅ Add this field

    private String aboutHospital;


    private String status;

    // Used for sorting hospitals by rating
    private Double rating;

    // Used for sorting hospitals by number of doctors
    private Integer doctorCount;



    @Column(name="is_active")
    private Boolean isActive;

    @ElementCollection
    @CollectionTable(
            name = "hospital_rooms",
            joinColumns = @JoinColumn(name = "hospital_id")
    )
    private List<Room> rooms = new ArrayList<>();

    @Column(name = "created_time", updatable = false)
    private String createdTime;

    @Column(name = "updated_time")
    private String updatedTime;

    //Mandatory:-we’re enforcing that the password field is mandatory for every hospital record
    @Column(nullable = false)
    private String password;

    @PrePersist
    protected void onCreate() {
        createdTime = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
        updatedTime = createdTime;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTime = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
    }

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Room {

        private String roomNumber;
        private String roomType;
        private boolean available;

    }
}



