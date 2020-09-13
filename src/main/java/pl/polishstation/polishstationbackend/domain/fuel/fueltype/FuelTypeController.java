package pl.polishstation.polishstationbackend.domain.fuel.fueltype;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polishstation.polishstationbackend.apiutils.stringified.StringifiedDomainController;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.dto.FuelTypeDTO;

@Loggable
@RestController
@RequestMapping("/fuel-type")
public class FuelTypeController extends StringifiedDomainController<FuelType, FuelTypeDTO> {
}
