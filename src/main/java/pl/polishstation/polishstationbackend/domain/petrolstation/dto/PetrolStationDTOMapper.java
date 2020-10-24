package pl.polishstation.polishstationbackend.domain.petrolstation.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainDTOMapper;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelType;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Mapper
public interface PetrolStationDTOMapper extends BasicDomainDTOMapper<PetrolStation, PetrolStationDTO> {

    @Mapping(source = "fuelPrice", target = "fuelPriceDTO")
    PetrolStationDTO convertIntoDTO(PetrolStation petrolStation);

    PetrolStation convertIntoObject(PetrolStationDTO petrolStationDTO);

    @Mapping(target = "fuelType", source = "fuelType.name")
    LastFuelPriceDTO convertIntoLastFuelPriceDTO(FuelPrice fuelPrice);

    default Optional<Double> convertOptional(Double d) {
        return Optional.ofNullable(d);
    }

    default List<String> convertFuelTypeIntoString(List<FuelType> fuelTypeList) {
        return fuelTypeList.stream()
                .map(FuelType::getName)
                .collect(Collectors.toList());
    }

    default String fuelTypeToString(FuelType fuelType) {
        return fuelType.getName();
    }

    default List<FuelType> convertStringIntoFuelType(List<String> fuelTypeNames) {
        if(isNull(fuelTypeNames))
            fuelTypeNames = new LinkedList<>();
        return fuelTypeNames.stream()
                .map(FuelType::new)
                .collect(Collectors.toList());
    }
}
