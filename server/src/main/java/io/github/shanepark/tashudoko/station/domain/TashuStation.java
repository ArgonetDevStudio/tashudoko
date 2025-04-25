package io.github.shanepark.tashudoko.station.domain;

import io.github.shanepark.tashudoko.configuration.AppConfig;
import io.github.shanepark.tashudoko.station.domain.dto.TashuStationDto;
import io.github.shanepark.tashudoko.station.domain.dto.TashuStatusResult;
import io.github.shanepark.tashudoko.utils.DistanceCalculator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public class TashuStation implements Comparable<TashuStation> {
    private final String id;
    private final String name;
    private final double latitude;
    private final double longitude;
    private final String address;
    private final int distanceMeter;
    private int parkingCount;

    public TashuStation(TashuStatusResult r, AppConfig appConfig) {
        this.id = r.id();
        this.name = r.name();
        this.latitude = Double.parseDouble(r.xPos());
        this.longitude = Double.parseDouble(r.yPos());
        this.address = r.address();
        this.distanceMeter = calcDistance(appConfig);
        this.parkingCount = r.parkingCount();
    }

    private int calcDistance(AppConfig appConfig) {
        double distanceInKilometer = DistanceCalculator.calculateDistance(appConfig.baseLatitude(), appConfig.baseLongitude()
                , this.latitude, this.longitude);
        return (int) (distanceInKilometer * 1000);
    }

    public void updateParkingCount(int n) {
        this.parkingCount = n;
    }

    @Override
    public int compareTo(TashuStation o) {
        return Integer.compare(this.distanceMeter, o.distanceMeter);
    }

    public TashuStationDto toDto() {
        return new TashuStationDto(
                this.name,
                this.address,
                this.latitude,
                this.longitude,
                this.distanceMeter,
                this.parkingCount
        );
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TashuStation that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public boolean hasBike() {
        return parkingCount > 0;
    }

}
