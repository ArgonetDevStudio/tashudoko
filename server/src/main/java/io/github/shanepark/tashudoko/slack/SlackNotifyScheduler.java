package io.github.shanepark.tashudoko.slack;

import io.github.shanepark.tashudoko.configuration.AppConfig;
import io.github.shanepark.tashudoko.station.service.TashuStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SlackNotifyScheduler {
    private final TashuStationService tashuStationService;
    private final SlackNotifier slackNotifier;
    private final AppConfig appConfig;

    @Scheduled(cron = "0 0 18 * * MON-FRI")
    public void notifySlack() {
        var stations = tashuStationService.availableInDistance(appConfig.maxDistanceMeter());
        slackNotifier.notifyStations(stations);
    }

}
