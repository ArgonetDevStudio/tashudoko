package io.github.shanepark.tashudoko.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "slack")
public record SlackConfig(
        String url
) {
}
