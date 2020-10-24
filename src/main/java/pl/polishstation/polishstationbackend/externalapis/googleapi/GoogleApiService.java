package pl.polishstation.polishstationbackend.externalapis.googleapi;

import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.domain.localization.Localization;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.utils.geo.Location;

import java.io.IOException;
import java.util.List;

import static com.google.maps.GeocodingApi.geocode;
import static com.google.maps.PlacesApi.textSearchQuery;
import static java.lang.String.format;
import static java.util.Objects.isNull;

@Service
public class GoogleApiService {

    /**
     * Template string for making request to Geocoding API. Argumenst:
     * "Polska <województwo: string> <miasto: string> <ulica: string> <numer_domu: string>"
     */
    private static final String LOCALIZATION_TEMPLATE = "Polska %s %s %s %s";

    @Autowired
    GeoApiContext geoApiContext;

    public Location getLocationOfLocalization(LocalizationDTO localization) throws InterruptedException, ApiException, IOException {
        var results = geocode(geoApiContext, convertLocalizationToGeoString(localization)).await();
        return extractLocationFromResults(results);
    }

    public PlacesSearchResponse getPetrolStationsOfPosition(Location userLocation, int range) throws InterruptedException, ApiException, IOException {
        return  textSearchQuery(geoApiContext, PlaceType.GAS_STATION)
                .location(new LatLng(userLocation.getLat(), userLocation.get_long()))
                .radius(range)
                .await();
    }

    private Location extractLocationFromResults(GeocodingResult[] results) {
        return Location.builder()
                ._long(results[0].geometry.location.lng)
                .lat(results[0].geometry.location.lat)
                .build();
    }

    private String convertLocalizationToGeoString(LocalizationDTO localization) {
        if(isNull(localization.getStreet()) || localization.getStreet().length() < 1) {
            return format(LOCALIZATION_TEMPLATE,
                    localization.getProvince(),
                    localization.getName(),
                    localization.getStreet(),
                    "");
        } else {
            return format(LOCALIZATION_TEMPLATE,
                    localization.getProvince(),
                    localization.getName(),
                    localization.getStreet(),
                    localization.getNumber());
        }

    }
}
