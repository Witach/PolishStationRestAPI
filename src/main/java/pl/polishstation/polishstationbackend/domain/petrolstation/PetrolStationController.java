package pl.polishstation.polishstationbackend.domain.petrolstation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.apiutils.BasicDomainController;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationPostDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Loggable
@RestController
@RequestMapping("/petrol-station")
public class PetrolStationController extends BasicDomainController<PetrolStation, PetrolStationDTO, PetrolStationPostDTO> {
}
