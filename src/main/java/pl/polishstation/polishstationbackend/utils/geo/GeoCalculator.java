package pl.polishstation.polishstationbackend.utils.geo;

import static java.lang.Math.*;

public class GeoCalculator {

    public static double distance(Location pointA, Location pointB) {
        return Math.sqrt(Math.pow(pointA.get_long() - pointB.get_long(), 2) + Math.pow(pointA.getLat() - pointB.getLat(), 2));
    }

    private static double powOfSinDistance(double value) {
        return pow(sin(value / 2), 2);
    }
}
