package io.github.shanepark.tashudoko.station.service;

import io.github.shanepark.tashudoko.station.domain.TashuStation;
import io.github.shanepark.tashudoko.station.domain.dto.TashuStationDto;
import io.github.shanepark.tashudoko.station.repository.TashuStationRepository;
import io.github.shanepark.tashudoko.tashuapi.TashuApiClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TashuStationServiceTest {

    @InjectMocks
    private TashuStationService tashuStationService;

    @Mock
    private TashuStationRepository tashuStationRepository;

    @Mock
    private TashuApiClient tashuApiClient;

    @Test
    void availableInDistance() {
    }

    @Test
    void findAll() {
        // Given
        when(tashuStationRepository.findAll()).thenReturn(List.of(
                dummyDto("Station A", 100),
                dummyDto("Station B", 200),
                dummyDto("Station C", 300)
        ));

        // When
        List<TashuStationDto> all = tashuStationService.findAll();

        // Then
        assertThat(all).hasSize(3);
    }

    @Test
    void findAllInDistanceOrderAvailableInDistanceWhereParkingCountIsGreaterThan() {
        // Given
        int maxDistance = 400;
        when(tashuStationRepository.findAllInDistanceAndHasBikeOrderByDistance(maxDistance)).thenReturn(List.of(
                dummyDto("Station A", 100),
                dummyDto("Station B", 200),
                dummyDto("Station C", 300)
        ));

        // When
        List<TashuStationDto> byDistance = tashuStationService.availableInDistance(maxDistance);

        // Then
        assertThat(byDistance).hasSize(3);
    }

    private TashuStation dummyDto(String name, int distance) {
        return new TashuStation(name, null, 0, 0, "", distance);
    }

}
