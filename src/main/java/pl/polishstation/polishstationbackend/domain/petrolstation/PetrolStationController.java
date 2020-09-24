package pl.polishstation.polishstationbackend.domain.petrolstation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polishstation.polishstationbackend.apiutils.paged.PagedDomainController;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationPostDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

@Loggable
@RestController
@RequestMapping("/petrol-station")
public class PetrolStationController extends PagedDomainController<PetrolStation, PetrolStationDTO, PetrolStationPostDTO> {

}
