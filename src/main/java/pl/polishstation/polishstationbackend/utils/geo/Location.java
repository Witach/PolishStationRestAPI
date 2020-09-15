package pl.polishstation.polishstationbackend.utils.geo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private Double lat;
    private Double _long;

    public double getRadiansLat() {
        return  Math.toRadians(getLat());
    }

    public double getRadiansLong() {
        return Math.toRadians(get_long());
    }

}
