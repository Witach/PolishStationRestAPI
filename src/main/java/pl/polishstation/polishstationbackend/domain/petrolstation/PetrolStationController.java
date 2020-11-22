package pl.polishstation.polishstationbackend.domain.petrolstation;

import com.google.maps.errors.ApiException;
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
import pl.polishstation.polishstationbackend.aspect.ScorePoints;
import pl.polishstation.polishstationbackend.domain.opinion.Opinion;
import pl.polishstation.polishstationbackend.domain.opinion.dto.OpinionDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationPostDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserService;

import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;

@Loggable
@RestController
@RequestMapping("/petrol-station")
@CrossOrigin
public class PetrolStationController {
    @Autowired
    PetrolStationService service;

    @Autowired
    AppUserService appUserService;

    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public PetrolStationDTO get(@PathVariable Long id) {
        return service.getEntityById(id);
    }

    @ResponseStatus(OK)
    @GetMapping
    public List<PetrolStationDTO> get(Sort sort, @RequestParam MultiValueMap<String, String> filtringParams) throws InterruptedException, ApiException, IOException {
        return service.searchForPetrolStations(sort, filtringParams);
    }

    @ScorePoints
    @ResponseStatus(CREATED)
    @PatchMapping("/{id}")
    public void update(@Valid @RequestBody PetrolStationPostDTO dto, @PathVariable Long id) {
        appUserService.addEditingPoint();
        service.updateEntity(dto, id);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEntity(id);
    }

    @ScorePoints
    @ResponseStatus(CREATED)
    @PostMapping
    public PetrolStationDTO post(@Valid @RequestBody PetrolStationPostDTO dto) {
        appUserService.addCreationToUser();
        return service.addEntity(dto);
    }

    @ResponseStatus(OK)
    @GetMapping("/{id}/opinions")
    public List<OpinionDTO> getLatestOpinions(@PathVariable Long id) {
        return service.getLatestOpinionsOfPetrolStation(id);
    }
}
