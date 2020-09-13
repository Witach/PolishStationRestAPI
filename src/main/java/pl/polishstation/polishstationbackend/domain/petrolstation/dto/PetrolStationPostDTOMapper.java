package pl.polishstation.polishstationbackend.domain.petrolstation.dto;

import org.mapstruct.Mapper;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainPostDTOMapper;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelType;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface PetrolStationPostDTOMapper extends BasicDomainPostDTOMapper<PetrolStation, PetrolStationPostDTO> {
    PetrolStation convertIntoObject(PetrolStationPostDTO petrolStationPostDTO);
    default List<FuelType> convertStringIntoFuelType(List<String> fuelTypeNames) {
        return fuelTypeNames.stream()
                .map(FuelType::new)
                .collect(Collectors.toList());
    }
}
