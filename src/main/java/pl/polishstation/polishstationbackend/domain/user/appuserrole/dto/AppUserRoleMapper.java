package pl.polishstation.polishstationbackend.domain.user.appuserrole.dto;

import org.mapstruct.Mapper;
import pl.polishstation.polishstationbackend.apiutils.stringified.StringifiedDomainMapper;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRole;

@Mapper
public interface AppUserRoleMapper extends StringifiedDomainMapper<AppUserRole, AppUserRoleDTO> {
    AppUserRoleDTO convertIntoDTO(AppUserRole appUserRole);
    AppUserRole convertIntoObject(AppUserRoleDTO appUserRoleDTO);
    default String convertIntoString(AppUserRole appUserRole) {
        return appUserRole.getName();
    }
}
