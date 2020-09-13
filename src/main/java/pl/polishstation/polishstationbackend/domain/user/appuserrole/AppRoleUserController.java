package pl.polishstation.polishstationbackend.domain.user.appuserrole;

import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.apiutils.stringified.StringifiedDomainController;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.dto.AppUserRoleDTO;

@Loggable
@RestController
@RequestMapping("/app-user-role")
public class AppRoleUserController extends StringifiedDomainController<AppUserRole, AppUserRoleDTO> {
}
