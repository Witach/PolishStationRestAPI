package pl.polishstation.polishstationbackend.domain.user.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainController;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserPostDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserStatsDTO;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@Loggable
@RestController
@RequestMapping("/app-user")
public class AppUserController extends BasicDomainController<AppUser, AppUserDTO, AppUserPostDTO> {
    @Autowired
    AppUserService appUserService;

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
}
