package io.github.shanepark.tashudoko.utils;

public class DistanceCalculator {

    private static final double EARTH_RADIUS_KILOMETERS = 6371.0;

    /**
     * @param latitude1  latitude of the first point
     * @param longitude1 longitude of the first point
     * @param latitude2  latitude of the second point
     * @param longitude2 longitude of the second point
     * @return distance in kilometers
     */
    public static double calculateDistance(
            double latitude1, double longitude1,
            double latitude2, double longitude2
    ) {
        double latitudeDifferenceInRadians = Math.toRadians(latitude2 - latitude1);
        double longitudeDifferenceInRadians = Math.toRadians(longitude2 - longitude1);
        double latitude1InRadians = Math.toRadians(latitude1);
        double latitude2InRadians = Math.toRadians(latitude2);
        double haversineFormula = Math.sin(latitudeDifferenceInRadians / 2) * Math.sin(latitudeDifferenceInRadians / 2)
                + Math.cos(latitude1InRadians) * Math.cos(latitude2InRadians)
                * Math.sin(longitudeDifferenceInRadians / 2) * Math.sin(longitudeDifferenceInRadians / 2);
        return EARTH_RADIUS_KILOMETERS * 2 * Math.atan2(Math.sqrt(haversineFormula), Math.sqrt(1 - haversineFormula));
    }

}
