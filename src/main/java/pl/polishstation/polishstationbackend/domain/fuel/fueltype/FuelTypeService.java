package pl.polishstation.polishstationbackend.domain.fuel.fueltype;

import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.apiutils.stringified.StringifiedDomainService;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.dto.FuelTypeDTO;

import java.util.stream.Collectors;

@Service
public class FuelTypeService extends StringifiedDomainService<FuelType, FuelTypeDTO> {

    public void deleteEntity(String name) {
        ((FuelTypeRepository)repository).deleteFuelTypeByNameEquals(name);
    }

    public void persistsDefaultFuelTypes() {
        var fuelTypes = FuelTypesNames.FUEL_TYPES_LIST.stream()
                .map(FuelType::new)
                .collect(Collectors.toList());

        repository.saveAll(fuelTypes);
    }
}
