package io.github.shanepark.tashudoko.station.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.shanepark.tashudoko.configuration.AppConfig;
import io.github.shanepark.tashudoko.station.domain.TashuStation;
import io.github.shanepark.tashudoko.station.domain.dto.TashuApiResponse;
import io.github.shanepark.tashudoko.tashuapi.TashuApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TashuStationRepositoryTest {

    private TashuStationRepository repository;

    @Mock
    private TashuApiClient tashuApiClient;

    @BeforeEach
    void setUp() {
        AppConfig appConfig = new AppConfig(
                "argonet",
                36.368725,
                127.380790,
                400
        );
        repository = new TashuStationRepository(appConfig, tashuApiClient);
    }

    /**
     * TashuStation{id='ST0020'name='만년동 기업은행', distanceMeter=74, parkingCount=1},
     * TashuStation{id='ST0451'name='만년동 크리스탈웨딩홀', distanceMeter=76, parkingCount=2},
     * TashuStation{id='ST0449'name='만년동 초원아파트105동 육교 밑', distanceMeter=140, parkingCount=0},
     * TashuStation{id='ST0452'name='만년동 빽다방', distanceMeter=156, parkingCount=1},
     * TashuStation{id='ST0453'name='만년동 엑스포오피스텔', distanceMeter=218, parkingCount=2},
     * TashuStation{id='ST0454'name='만년동 수정빌딩 주차타워', distanceMeter=309, parkingCount=2},
     * TashuStation{id='ST0455'name='만년동 왕가한정식', distanceMeter=323, parkingCount=2}
     */
    @Test
    void findAllInDistanceTest() throws IOException {
        // Given
        Path path = Paths.get("src/test/resources/tashu_status.json");
        String json = Files.readString(path);
        TashuApiResponse response = new ObjectMapper().readValue(json, TashuApiResponse.class);

        // When
        when(tashuApiClient.getStations()).thenReturn(response);
        List<TashuStation> byDistance = repository.findAllInDistanceAndHasBikeOrderByDistance(400);

        // Then
        assertThat(byDistance).hasSize(6);
        assertThat(byDistance).contains(dummyStation("ST0020"));
        assertThat(byDistance.get(1)).isEqualTo(dummyStation("ST0451"));
        assertThat(byDistance).doesNotContain(dummyStation("ST0449"));
    }

    private static TashuStation dummyStation(String id) {
        return new TashuStation(id, null, 0, 0, null, 0);
    }

}
