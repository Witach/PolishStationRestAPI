package pl.polishstation.polishstationbackend.domain.user.appuser;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainController;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserPostDTO;

@Loggable
@RestController
@RequestMapping("/app-user")
public class AppUserController extends BasicDomainController<AppUser, AppUserDTO, AppUserPostDTO> {
}
