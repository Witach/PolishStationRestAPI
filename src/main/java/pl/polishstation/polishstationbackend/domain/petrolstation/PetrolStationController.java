package pl.polishstation.polishstationbackend.domain.petrolstation;

import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainController;
import pl.polishstation.polishstationbackend.apiutils.filtring.FilterDomainController;
import pl.polishstation.polishstationbackend.apiutils.filtring.FilterDomainService;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationPostDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

@Loggable
@RestController
@RequestMapping("/petrol-station")
public class PetrolStationController extends FilterDomainController<PetrolStation, PetrolStationDTO, PetrolStationPostDTO> {
}
