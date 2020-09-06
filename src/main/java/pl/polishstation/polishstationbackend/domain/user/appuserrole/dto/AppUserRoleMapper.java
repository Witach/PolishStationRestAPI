package pl.polishstation.polishstationbackend.domain.user.appuserrole.dto;

import org.mapstruct.Mapper;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRole;

@Mapper
public interface AppUserRoleMapper {
    AppUserRoleDTO convertIntoDTO(AppUserRole appUserRole);
    AppUserRole convertIntoObject(AppUserRoleDTO appUserRoleDTO);
    default String convertIntoString(AppUserRole appUserRole) {
        return appUserRole.getName();
    }
}
