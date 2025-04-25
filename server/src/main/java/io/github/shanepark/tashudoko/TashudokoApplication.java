package io.github.shanepark.tashudoko;

import io.github.shanepark.tashudoko.configuration.AppConfig;
import io.github.shanepark.tashudoko.configuration.TashuApiConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class TashudokoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TashudokoApplication.class, args);
    }

    private final AppConfig appConfig;
    private final TashuApiConfig tashuApiConfig;

    @PostConstruct
    public void init() {
        log.info("=========================================");
        log.info("AppConfig: {}", appConfig);
        log.info("TashuApiClient: {}", tashuApiConfig);
    }

}
