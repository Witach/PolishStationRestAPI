package pl.polishstation.polishstationbackend.domain.user.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainController;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.*;
import pl.polishstation.polishstationbackend.domain.user.verification.RegisterService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static pl.polishstation.polishstationbackend.utils.ExtractAppUrl.extractAppUrl;

@Loggable
@RestController
@CrossOrigin
@RequestMapping("/app-user")
public class AppUserController extends BasicDomainController<AppUser, AppUserDTO, AppUserPostDTO> {
    @Autowired
    AppUserService appUserService;

    @Autowired
    RegisterService registerService;

    @ResponseStatus(CREATED)
    @PatchMapping("/{id}")
    @Override
    public void update(@Valid @RequestBody AppUserPostDTO dto, @PathVariable Long id) {
        service.updateEntity(dto, id);
    }

    @ResponseStatus(OK)
    @GetMapping("/{id}/stats")
    public AppUserStatsDTO getStats(@PathVariable Long id) {
        return appUserService.getUserStats(id);
    }

    @ResponseStatus(OK)
    @GetMapping("/{email}/resemble")
    public void resemblePassword(@PathVariable String email, HttpServletRequest request) throws MessagingException {
        var url = extractAppUrl(request);
        registerService.resemblePassword(email, url);
    }

    @ResponseStatus(CREATED)
    @PatchMapping("/{email}/update-password/{jwt}")
    public void update(@Valid @RequestBody AppUserPostDTO dto, @PathVariable String email, @PathVariable String jwt) {
        appUserService.updatePassword(dto, email, jwt);
    }

    @ResponseStatus(OK)
    @PostMapping("/{email}/loved-stations")
    public void postLovedStations(@PathVariable String email, @RequestBody LovedPetrolStationDTO lovedPetrolStationDTO) {
        appUserService.attachLovedPetrolStation(email, lovedPetrolStationDTO);
    }

    @ResponseStatus(OK)
    @DeleteMapping("/{email}/loved-stations/{id}")
    public void deleteLovedStations(@PathVariable String email, @PathVariable long id) {
        appUserService.discardLovedPetrolStation(email, new LovedPetrolStationDTO(id));
    }

    @ResponseStatus(OK)
    @GetMapping("/{email}/loved-stations")
    public List<PetrolStationDTO> getLovedStations(@PathVariable String email) {
        return appUserService.getLovedPetrolStaitonsOfUser(email);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/{email}/fire-token")
    public void update(@Valid @RequestBody TokenRequest dto, @PathVariable String email) {
        appUserService.updateToken(dto, email);
    }

}
