package pl.polishstation.polishstationbackend.domain.fuel.fuelprice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainController;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPriceDTO;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.dto.FuelPricePostDTO;

@Loggable
@RestController
@RequestMapping("/fuel-price")
public class FuelPriceController extends BasicDomainController<FuelPrice, FuelPriceDTO, FuelPricePostDTO> {
}
