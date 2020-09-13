package pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainDTOMapper;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelType;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface FuelPriceDTOMapper extends BasicDomainDTOMapper<FuelPrice, FuelPriceDTO> {
    @Override
    @Mapping(target = "fuelType", source = "fuelType.name")
    FuelPriceDTO convertIntoDTO(FuelPrice fuelPrice);

    @Override
    @Mapping(source = "fuelType", target = "fuelType.name")
    FuelPrice convertIntoObject(FuelPriceDTO fuelPriceDTO);

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
