package nimblix.in.HealthCareHub.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponse {
    private String roomNumber;
    private String roomType;
    private boolean available;
}
