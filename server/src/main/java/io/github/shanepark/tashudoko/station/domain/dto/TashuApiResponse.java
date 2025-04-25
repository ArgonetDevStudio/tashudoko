package io.github.shanepark.tashudoko.station.domain.dto;

import java.util.List;

public record TashuApiResponse(
        int count,
        Object next,
        Object previous,
        List<TashuStatusResult> results
) {
}
