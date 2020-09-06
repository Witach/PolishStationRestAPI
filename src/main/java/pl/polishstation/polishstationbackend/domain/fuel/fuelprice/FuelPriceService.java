package pl.polishstation.polishstationbackend.domain.fuel.fuelprice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPricePostDTO;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPricePostDTOMapper;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelTypeRepository;
import pl.polishstation.polishstationbackend.exception.EntityDoesNotExists;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPriceDTO;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPriceDTOMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuelPriceService {

    @Autowired
    FuelPriceDTOMapper mapper;

    @Autowired
    FuelPricePostDTOMapper postDTOMapper;

    @Autowired
    FuelPriceRepository repository;

    public FuelPriceDTO addEntity(FuelPricePostDTO dto) {
        var newFuelPrice = postDTOMapper.convertIntoObject(dto);
        return mapper.convertIntoDTO(
                repository.save(newFuelPrice)
        );
    }

    public void deleteEntity(Long id) {
        if(!repository.existsById(id))
            throw new EntityDoesNotExists();
        repository.deleteById(id);
    }

    public void updateEntity(FuelPricePostDTO dto, Long id) {
        if(!repository.existsById(id))
            throw new EntityDoesNotExists();
        var fuelPrice = postDTOMapper.convertIntoObject(dto);
        repository.save(fuelPrice);
    }

    public FuelPriceDTO getEntityById(Long id) {
        return repository.findById(id)
                .map(mapper::convertIntoDTO)
                .orElseThrow(EntityDoesNotExists::new);
    }

    public List<FuelPriceDTO> getAllEntities() {
        return repository.findAll().stream()
                .map(mapper::convertIntoDTO)
                .collect(Collectors.toList());
    }
}
