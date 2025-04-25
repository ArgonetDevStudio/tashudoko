package io.github.shanepark.tashudoko.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config")
public record AppConfig(
        String baseName,
        double baseLatitude,
        double baseLongitude,
        int maxDistanceMeter
) {
}
