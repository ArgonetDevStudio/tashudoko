package io.github.shanepark.tashudoko.slack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.shanepark.tashudoko.configuration.SlackConfig;
import io.github.shanepark.tashudoko.station.domain.dto.TashuStationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class SlackNotifier {

    private final SlackConfig slackConfig;
    private final ObjectMapper mapper = new ObjectMapper();

    public void notifyStations(List<TashuStationDto> stations) {
        String payload = makeSlackPayload(stations);
        sendMessage(payload);
    }

    private String makeSlackPayload(List<TashuStationDto> stations) {
        LocalDateTime now = LocalDateTime.now();
        String dateText = String.format(" (%d-%02d-%02d (%s) %02d:%02d)",
                now.getYear(), now.getMonthValue(), now.getDayOfMonth(), dayOfWeekKor(now), now.getHour(), now.getMinute()
        );

        String header = "*가까운 이용 가능 타슈 목록 🚲*";
        if (stations.isEmpty()) {
            header = "*현재 근처에 이용 가능한 타슈가 없습니다 \uD83D\uDEB3*";
        }

        List<Object> blocks = new ArrayList<>();
        blocks.add(Map.of(
                "type", "section",
                "text", Map.of("type", "mrkdwn", "text", header + dateText)
        ));
        addDivider(blocks);

        for (int i = 0; i < stations.size(); i++) {
            blocks.add(makeSection(stations, i));
            addDivider(blocks);
        }

        try {
            return mapper.writeValueAsString(Map.<String, Object>of("blocks", blocks));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to make Slack Payload", e);
        }
    }

    private static void addDivider(List<Object> blocks) {
        blocks.add(Map.of("type", "divider"));
    }

    private static String dayOfWeekKor(LocalDateTime now) {
        return switch (now.getDayOfWeek()) {
            case MONDAY -> "월";
            case TUESDAY -> "화";
            case WEDNESDAY -> "수";
            case THURSDAY -> "목";
            case FRIDAY -> "금";
            case SATURDAY -> "토";
            case SUNDAY -> "일";
        };
    }

    private static Map<String, Object> makeSection(List<TashuStationDto> stations, int i) {
        TashuStationDto station = stations.get(i);
        String availableIcon = availableIcon(station.parkingCount());
        String stationInfo = String.format(
                "*%s %d. %s*\n%s (%dm)\n대여가능: `%d`",
                availableIcon, i + 1, station.name(), station.address(), station.distance(), station.parkingCount()
        );
        return Map.of(
                "type", "section",
                "text", Map.of("type", "mrkdwn", "text", stationInfo),
                "accessory", Map.of(
                        "type", "button",
                        "text", Map.of("type", "plain_text", "text", "위치보기(PC)", "emoji", true),
                        "url", String.format("https://maps.naver.com?query=%f%%20%f", station.latitude(), station.longitude())
                )
        );
    }

    private final static String RED_CIRCLE = "\uD83D\uDD34";
    private final static String ORANGE_CIRCLE = "\uD83D\uDFE0";
    private final static String YELLOW_CIRCLE = "\uD83D\uDFE1";
    private final static String GREEN_CIRCLE = "\uD83D\uDFE2";

    private static String availableIcon(int availableCnt) {
        return switch (availableCnt) {
            case 0 -> RED_CIRCLE;
            case 1 -> ORANGE_CIRCLE;
            case 2 -> YELLOW_CIRCLE;
            default -> GREEN_CIRCLE;
        };
    }

    private void sendMessage(String jsonPayload) {
        log.info("Sending message to Slack: {}", jsonPayload);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(slackConfig.url()))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Slack response: {}", response.statusCode());
        } catch (Exception e) {
            log.error("Error sending message to Slack", e);
            throw new RuntimeException(e);
        }
    }

}
