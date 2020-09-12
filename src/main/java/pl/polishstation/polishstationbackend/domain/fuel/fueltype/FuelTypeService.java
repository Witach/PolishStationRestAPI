package pl.polishstation.polishstationbackend.domain.fuel.fueltype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.apiutils.StringifiedDomainService;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.dto.FuelTypeMapper;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.dto.FuelTypeDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
