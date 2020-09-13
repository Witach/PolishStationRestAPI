package pl.polishstation.polishstationbackend.domain.fuel.fuelprice;

import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainService;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPriceDTO;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPricePostDTO;

@Service
public class FuelPriceService extends BasicDomainService<FuelPrice, FuelPriceDTO, FuelPricePostDTO> {
}
