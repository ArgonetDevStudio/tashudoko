package io.github.shanepark.tashudoko.station.domain.dto;

public record TashuStationDto(
        String name,
        String address,
        double latitude,
        double longitude,
        int distance,
        int parkingCount
) {
}
