package io.github.shanepark.tashudoko.station.controller;

import io.github.shanepark.tashudoko.configuration.AppConfig;
import io.github.shanepark.tashudoko.slack.SlackNotifier;
import io.github.shanepark.tashudoko.station.service.TashuStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/slack")
@RequiredArgsConstructor
public class SlackApiController {
    private final TashuStationService tashuStationService;
    private final SlackNotifier slackNotifier;
    private final AppConfig appConfig;

    @GetMapping("/notify")
    public void notifySlack() {
        var stations = tashuStationService.availableInDistance(appConfig.maxDistanceMeter());
        slackNotifier.notifyStations(stations);
    }

}
