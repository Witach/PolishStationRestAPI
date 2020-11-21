package pl.polishstation.polishstationbackend.domain.fuel.fuelprice;

import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainService;
import pl.polishstation.polishstationbackend.aspect.ScorePoints;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPriceDTO;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPricePostDTO;

import java.time.LocalDateTime;

@Service
public class FuelPriceService extends BasicDomainService<FuelPrice, FuelPriceDTO, FuelPricePostDTO> {

    @ScorePoints
    @Override
    public FuelPriceDTO addEntity(FuelPricePostDTO fuelPricePostDTO) {
        var fuelPrice = postDTOMapper.convertIntoObject(fuelPricePostDTO);
        fuelPrice.setDate(LocalDateTime.now());
        return mapper.convertIntoDTO(
                repository.save(fuelPrice)
        );
    }
}
