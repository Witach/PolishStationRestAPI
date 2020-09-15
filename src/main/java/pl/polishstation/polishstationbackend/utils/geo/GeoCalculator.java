package pl.polishstation.polishstationbackend.utils.geo;

import static java.lang.Math.*;

public class GeoCalculator {

    public static double distance(Location pointA, Location pointB) {
        double dlon = pointB.getRadiansLong() - pointA.getRadiansLong();
        double dlat = pointB.getRadiansLat() - pointA.getRadiansLat();
        double a = powOfSinDistance(dlat) + cos(pointA.getLat()) * cos(pointB.getLat()) * powOfSinDistance(dlon);
        double c = 2 * asin(sqrt(a));
        double r = 6371;
        return(c * r);
    }

    private static double powOfSinDistance(double value) {
        return pow(sin(value / 2), 2);
    }
}
