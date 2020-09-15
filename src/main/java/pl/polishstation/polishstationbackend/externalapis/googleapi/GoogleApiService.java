package pl.polishstation.polishstationbackend.externalapis.googleapi;

import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.domain.localization.Localization;
import pl.polishstation.polishstationbackend.utils.geo.Location;

import java.io.IOException;

import static com.google.maps.GeocodingApi.geocode;
import static java.lang.String.format;

@Service
public class GoogleApiService {

    /**
     * Template string for making request to Geocoding API. Argumenst:
     * "Polska <województwo: string> <miasto: string> <ulica: string> <numer_domu: string>"
     */
    private static final String LOCALIZATION_TEMPLATE = "Polska %s %s %s %s";

    @Autowired
    GeoApiContext geoApiContext;

    public Location getLocationOfLocalization(Localization localization) throws InterruptedException, ApiException, IOException {
        var results = geocode(geoApiContext, convertLocalizationToGeoString(localization)).await();
        return extractLocationFromResults(results);
    }

    private Location extractLocationFromResults(GeocodingResult[] results) {
        return Location.builder()
                ._long(results[0].geometry.location.lat)
                .lat(results[0].geometry.location.lng)
                .build();
    }

    private String convertLocalizationToGeoString(Localization localization) {
        return format(LOCALIZATION_TEMPLATE,
                localization.getProvince(),
                localization.getName(),
                localization.getStreet(),
                localization.getNumber());
    }
}
