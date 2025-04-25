package io.github.shanepark.tashudoko.station.domain.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TashuApiResponseTest {

    @Test
    public void parse() throws IOException {
        // Given
        Path path = Paths.get("src/test/resources/tashu_status.json");
        String json = Files.readString(path);

        // When
        TashuApiResponse response = new ObjectMapper().readValue(json, TashuApiResponse.class);

        // Then
        assertThat(response.count()).isEqualTo(1281);
        assertThat(response.previous()).isNull();
        assertThat(response.next()).isNull();
        List<TashuStatusResult> result = response.results();
        assertThat(result).hasSize(1281);

        TashuStatusResult first = result.getFirst();
        assertThat(first.id()).isEqualTo("ST0003");
        assertThat(first.name()).isEqualTo("탄방동 한사랑병원");
        assertThat(first.nameEn()).isEqualTo("탄방동 한사랑병원");
        assertThat(first.nameCn()).isEqualTo("탄방동 한사랑병원");
        assertThat(first.xPos()).isEqualTo("36.348446");
        assertThat(first.yPos()).isEqualTo("127.390052");
        assertThat(first.address()).isEqualTo("탄방동 730");
        assertThat(first.parkingCount()).isEqualTo(1);
    }

}
