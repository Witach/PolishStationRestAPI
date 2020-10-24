package pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi;

import org.mapstruct.*;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public abstract class InfrastructureFuelStationDTOMapper {

    @Mappings({
            @Mapping(target = "localization.province", source = "regionName"),
            @Mapping(target = "localization.name", source = "town"),
            @Mapping(target = "name", source = "fuelStationName"),
            @Mapping(target = "localization.street", source = "address"),
            @Mapping(target = "localization.postalCode", source = "addressCode"),
    })
    public abstract PetrolStation convertIntoObject(InfrastructureFuelStationDTO dto);

    @AfterMapping
    public void afterConvertIntoObject(InfrastructureFuelStationDTO dto, @MappingTarget PetrolStation petrolStation) {
       petrolStation.getLocalization().setPetrolStation(petrolStation);
    }
}
