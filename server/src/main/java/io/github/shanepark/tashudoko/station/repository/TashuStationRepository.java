package io.github.shanepark.tashudoko.station.repository;

import io.github.shanepark.tashudoko.configuration.AppConfig;
import io.github.shanepark.tashudoko.station.domain.TashuStation;
import io.github.shanepark.tashudoko.station.domain.dto.TashuApiResponse;
import io.github.shanepark.tashudoko.station.domain.dto.TashuStatusResult;
import io.github.shanepark.tashudoko.tashuapi.TashuApiClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TashuStationRepository {
    private final AppConfig appConfig;
    private final Map<String, TashuStation> stationMap = new HashMap<>();
    private final TashuApiClient tashuApiClient;

    @Getter
    private LocalDateTime lastApiCall;

    public List<TashuStation> findAll() {
        updateStations();
        return stationMap.values()
                .stream()
                .sorted()
                .toList();
    }

    public List<TashuStation> findAllInDistanceAndHasBikeOrderByDistance(int maxDistance) {
        updateStations();
        return stationMap.values()
                .stream()
                .filter(TashuStation::hasBike)
                .filter(s -> s.getDistanceMeter() <= maxDistance || maxDistance == 0)
                .sorted()
                .toList();
    }

    synchronized private void updateStations() {
        if (lastApiCall != null && lastApiCall.isAfter(LocalDateTime.now().minusSeconds(10))) {
            log.info("Tashu API call is skipped. lastCalled: {}", lastApiCall);
            return;
        }
        TashuApiResponse apiResponse = tashuApiClient.getStations();
        log.info("Tashu API called. lastCalled: {}", lastApiCall);

        for (TashuStatusResult r : apiResponse.results()) {
            TashuStation tashuStation = stationMap.get(r.id());
            if (tashuStation == null) {
                tashuStation = new TashuStation(r, appConfig);
                stationMap.put(r.id(), tashuStation);
            }
            tashuStation.updateParkingCount(r.parkingCount());
        }
        lastApiCall = LocalDateTime.now();
    }


}
