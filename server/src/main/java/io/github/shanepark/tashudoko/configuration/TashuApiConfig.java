package io.github.shanepark.tashudoko.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tashu-api")
public record TashuApiConfig(
        String url,
        String key
) {
}
