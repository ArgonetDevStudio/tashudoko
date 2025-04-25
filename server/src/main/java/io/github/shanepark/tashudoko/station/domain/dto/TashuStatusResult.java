package io.github.shanepark.tashudoko.station.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TashuStatusResult(
        String id,

        String name,

        @JsonProperty("name_en")
        String nameEn,

        @JsonProperty("name_cn")
        String nameCn,

        @JsonProperty("x_pos")
        String xPos,

        @JsonProperty("y_pos")
        String yPos,

        String address,

        @JsonProperty("parking_count")
        int parkingCount
) {
}
