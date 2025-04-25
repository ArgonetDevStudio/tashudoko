package io.github.shanepark.tashudoko.station.controller;

import io.github.shanepark.tashudoko.station.domain.dto.TashuStationDto;
import io.github.shanepark.tashudoko.station.service.TashuStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationApiController {
    private final TashuStationService service;

    @GetMapping(value = "/available")
    public List<TashuStationDto> available(@RequestParam int maxDistance) {
        return service.availableInDistance(maxDistance);
    }

    @GetMapping("/all")
    public List<TashuStationDto> all() {
        return service.findAll();
    }

}
