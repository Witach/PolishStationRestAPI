package pl.polishstation.polishstationbackend.domain.petrolstation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.apiutils.filtring.FilterDomainService;
import pl.polishstation.polishstationbackend.apiutils.paged.PagedDomainController;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationPostDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;

@Loggable
@RestController
@RequestMapping("/petrol-station")
public class PetrolStationController {
    @Autowired
    PetrolStationService service;

    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public PetrolStationDTO get(@PathVariable Long id) {
        return service.getEntityById(id);
    }

    @ResponseStatus(OK)
    @GetMapping
    public List<PetrolStationDTO> get(Sort sort, @RequestParam MultiValueMap<String, String> filtringParams) {
        return service.searchForPetrolStations(sort, filtringParams);
    }

    @ResponseStatus(CREATED)
    @PatchMapping("/{id}")
    public void update(@Valid @RequestBody PetrolStationPostDTO dto, @PathVariable Long id) {
        service.updateEntity(dto, id);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEntity(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public PetrolStationDTO post(@Valid @RequestBody PetrolStationPostDTO dto) {
        return service.addEntity(dto);
    }
}
