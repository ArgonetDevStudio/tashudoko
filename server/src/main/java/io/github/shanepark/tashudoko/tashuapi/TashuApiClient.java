package io.github.shanepark.tashudoko.tashuapi;

import io.github.shanepark.tashudoko.station.domain.dto.TashuApiResponse;
import org.springframework.web.service.annotation.GetExchange;

public interface TashuApiClient {

    @GetExchange("/station")
    TashuApiResponse getStations();

}
