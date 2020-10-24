package pl.polishstation.polishstationbackend.externalapis.googleapi;

import com.google.maps.model.PlacesSearchResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationPostDTO;

@Mapper
public interface GoogleMapper {

    @Mappings({
            @Mapping(source = "formattedAddress", target = "localization.formattedAddress"),
            @Mapping(source = "geometry.location.lat", target = "localization.lat"),
            @Mapping(source = "geometry.location.lng", target = "localization._long"),
            @Mapping(source = "name", target = "name"),
            @Mapping(target = "distance", source = ""),
            @Mapping(target = "dkn", source = ""),
            @Mapping(target = "fuelPriceDTO", source = ""),
            @Mapping(target = "fuelTypes", source = ""),
            @Mapping(target = "id", source = ""),
            @Mapping(target = "localization", source = ""),
            @Mapping(target = "petrolStationStats", source = "")
    })
    PetrolStationDTO convertFromGoogleDto(PlacesSearchResult placesSearchResult);
}
