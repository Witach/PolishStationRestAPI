package pl.polishstation.polishstationbackend.domain.petrolstation.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelType;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface PetrolStationForPetrolPriceMapper {

    PetrolStationForPetrolPrice convertIntoDTO(PetrolStation petrolStation);

    PetrolStation convertIntoObject(PetrolStationDTO petrolStationDTO);

    default List<String> fuelTypeIntoString(List<FuelType> fuelTypeList) {
        return fuelTypeList.stream()
                .map(FuelType::getName)
                .collect(Collectors.toList());
    }

    default List<FuelType> stringIntoFuelType( List<String> fuelTypeNames) {
        return fuelTypeNames.stream()
                .map(FuelType::new)
                .collect(Collectors.toList());
    }
}
