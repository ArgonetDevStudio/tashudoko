package io.github.shanepark.tashudoko.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DistanceCalculatorTest {

    @Test
    void calculateDistance() {
        double seoulLat = 37.5665;
        double seoulLon = 126.9780;
        double busanLat = 35.1796;
        double busanLon = 129.0756;

        double distanceFromSeoulToBusan = DistanceCalculator.calculateDistance(seoulLat, seoulLon, busanLat, busanLon);
        Assertions.assertThat((int) distanceFromSeoulToBusan).isEqualTo(325);
    }

}
