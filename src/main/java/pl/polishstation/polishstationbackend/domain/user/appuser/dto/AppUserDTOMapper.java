package pl.polishstation.polishstationbackend.domain.user.appuser.dto;

import org.mapstruct.Mapper;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainDTOMapper;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRole;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper
public interface AppUserDTOMapper extends BasicDomainDTOMapper<AppUser, AppUserDTO> {
    AppUserDTO convertIntoDTO(AppUser appUser);
    AppUser convertIntoObject(AppUserDTO appUserDTO);
    default List<String> appUserRoleIntoString(List<AppUserRole> appUserRoleList) {
        return appUserRoleList.stream()
                .map(AppUserRole::getName)
                .collect(Collectors.toList());
    }

    default List<AppUserRole> stringIntoAppUserRole(List<String> roleNames) {
        if(Objects.isNull(roleNames) || roleNames.isEmpty())
            return null;
        return roleNames.stream()
                .map(AppUserRole::new)
                .collect(Collectors.toList());
    }
}
