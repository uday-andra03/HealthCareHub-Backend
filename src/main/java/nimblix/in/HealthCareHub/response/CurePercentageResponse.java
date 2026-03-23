package nimblix.in.HealthCareHub.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class CurePercentageResponse {

        private Long doctorId;
        private Long totalPatients;
        private Long curedPatients;
        private Double curePercentage;

    }

