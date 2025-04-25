package io.github.shanepark.tashudoko.slack;

import io.github.shanepark.tashudoko.configuration.SlackConfig;
import io.github.shanepark.tashudoko.station.domain.dto.TashuStationDto;
import org.junit.jupiter.api.Test;

import java.util.List;

class SlackNotifierTest {

    String slackUrl = "https://hooks.slack.com/services/";
    String slackToken = "SLACK_TOKEN_HERE_FOR_TESTING";
    SlackConfig slackConfig = new SlackConfig(slackUrl + slackToken);
    SlackNotifier slackNotifier = new SlackNotifier(slackConfig);

    @Test
    void sendMessage_6_stations_available() {
        List<TashuStationDto> stations = List.of(
                new TashuStationDto("만년동 기업은행", "만년동 296", 36.368581, 127.379974, 74, 1),
                new TashuStationDto("만년동 크리스탈웨딩홀", "만년동 305", 36.369271, 127.381306, 76, 4),
                new TashuStationDto("만년동 초원아파트105동 육교 밑", "만년동 401", 36.368247, 127.379332, 140, 1),
                new TashuStationDto("만년동 빽다방", "만년동 390", 36.368537, 127.38252, 156, 2),
                new TashuStationDto("만년동 엑스포오피스텔", "만년동 384", 36.367285, 127.382452, 218, 6),
                new TashuStationDto("만년동 수정빌딩 주차타워", "만년동 25", 36.366118, 127.382011, 309, 1)
        );
        slackNotifier.notifyStations(stations);
    }

    @Test
    public void no_station_available() {
        List<TashuStationDto> stations = List.of();
        slackNotifier.notifyStations(stations);
    }

}
