package pl.polishstation.polishstationbackend.domain.fuel.fueltype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.dto.FuelTypeMapper;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.dto.FuelTypeDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FuelTypeService {

    @Autowired
    FuelTypeMapper mapper;
    @Autowired
    FuelTypeRepository repository;

    public FuelTypeDTO addEntity(FuelTypeDTO dto) {
        var fuelType = mapper.convertIntoObject(dto);
        return mapper.convertIntoDTO(
                repository.save(fuelType)
        );
    }

    public void deleteEntity(String name) {
        repository.deleteFuelTypeByNameEquals(name);
    }

    public List<String> getAllEntities() {
        return repository.findAll().stream()
                .map(FuelType::getName)
                .collect(Collectors.toList());
    }

    public void persistsDefaultFuelTypes() {
        var fuelTypes = FuelTypesNames.FUEL_TYPES_LIST.stream()
                .map(FuelType::new)
                .collect(Collectors.toList());

        repository.saveAll(fuelTypes);
    }

}
