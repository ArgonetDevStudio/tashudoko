package io.github.shanepark.tashudoko.station.service;

import io.github.shanepark.tashudoko.station.domain.TashuStation;
import io.github.shanepark.tashudoko.station.domain.dto.TashuStationDto;
import io.github.shanepark.tashudoko.station.repository.TashuStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TashuStationService {
    private final TashuStationRepository tashuStationRepository;

    public List<TashuStationDto> availableInDistance(int maxDistance) {
        return tashuStationRepository.findAllInDistanceAndHasBikeOrderByDistance(maxDistance)
                .stream()
                .map(TashuStation::toDto)
                .toList();
    }

    public List<TashuStationDto> findAll() {
        return tashuStationRepository.findAll()
                .stream()
                .map(TashuStation::toDto)
                .toList();
    }

}
