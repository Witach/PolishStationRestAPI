package pl.polishstation.polishstationbackend.domain.fuel.fueltype.dto;

import org.mapstruct.Mapper;
import pl.polishstation.polishstationbackend.apiutils.StringifiedDomainMapper;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelType;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface FuelTypeMapper extends StringifiedDomainMapper<FuelType, FuelTypeDTO> {
    FuelTypeDTO convertIntoDTO(FuelType fuelType);
    FuelType convertIntoObject(FuelTypeDTO fuelTypeDTO);
    default String convertIntoString(FuelType fuelType) {
        return fuelType.getName();
    }

    default List<String> convertIntoStringList(List<FuelType> fuelTypeList) {
        return fuelTypeList.stream()
                .map(FuelType::getName)
                .collect(Collectors.toList());
    }
}
