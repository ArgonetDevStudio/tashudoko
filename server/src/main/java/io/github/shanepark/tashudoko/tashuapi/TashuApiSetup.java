package io.github.shanepark.tashudoko.tashuapi;

import io.github.shanepark.tashudoko.configuration.TashuApiConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class TashuApiSetup {
    private final TashuApiConfig config;

    @Bean
    public TashuApiClient tashuApiClient() {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(10));
        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(1024 * 1024))
                .build();

        WebClient client = WebClient.builder()
                .baseUrl(config.url())
                .clientConnector(connector)
                .exchangeStrategies(strategies)
                .defaultHeader("api-token", config.key())
                .build();

        return HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(client))
                .build()
                .createClient(TashuApiClient.class);
    }

}
