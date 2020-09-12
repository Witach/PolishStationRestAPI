package pl.polishstation.polishstationbackend.domain.user.appuserrole;

import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.apiutils.StringifiedDomainService;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.dto.AppUserRoleDTO;

@Service
public class AppUserRoleService extends StringifiedDomainService<AppUserRole, AppUserRoleDTO> {
    public void deleteEntity(String name) {
        ((AppUserRoleRepository)repository).deleteAppUserRoleByNameEquals(name);
    }
}
