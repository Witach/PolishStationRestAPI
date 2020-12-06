package pl.polishstation.polishstationbackend.domain.user.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainController;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserPostDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserStatsDTO;
import pl.polishstation.polishstationbackend.domain.user.verification.RegisterService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
}
