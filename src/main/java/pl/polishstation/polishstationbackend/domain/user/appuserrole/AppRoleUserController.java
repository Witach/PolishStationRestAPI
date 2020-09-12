package pl.polishstation.polishstationbackend.domain.user.appuserrole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.apiutils.StringifiedDomainController;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.dto.AppUserRoleDTO;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

@Loggable
@RestController
@RequestMapping("/app-user-role")
public class AppRoleUserController extends StringifiedDomainController<AppUserRole, AppUserRoleDTO> {
}
