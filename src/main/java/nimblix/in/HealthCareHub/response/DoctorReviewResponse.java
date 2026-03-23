package nimblix.in.HealthCareHub.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorReviewResponse {

    private double averageRating;
    private long totalReviews;
}
