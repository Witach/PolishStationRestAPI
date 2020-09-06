package pl.polishstation.polishstationbackend.domain.petrolstation.dto;

import org.mapstruct.Mapper;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelType;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper
public interface PetrolStationDTOMapper {
    PetrolStationDTO convertIntoDTO(PetrolStation petrolStation);
    PetrolStation convertIntoObject(PetrolStationDTO petrolStationDTO);
    PetrolStation convertIntoObject(PetrolStationPostDTO petrolStationPostDTO);
    default Optional<Double> convertOptional(Double d) {
        return Optional.ofNullable(d);
    }
    default List<String> convertFuelTypeIntoString(List<FuelType> fuelTypeList) {
        return fuelTypeList.stream()
                .map(FuelType::getName)
                .collect(Collectors.toList());
    }

    default List<FuelType> convertStringIntoFuelType(List<String> fuelTypeNames) {
        return fuelTypeNames.stream()
                .map(FuelType::new)
                .collect(Collectors.toList());
    }
}
