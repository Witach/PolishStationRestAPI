package pl.polishstation.polishstationbackend.domain.user.appuserrole.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AppUserRoleDTO {
    @NotBlank
    @Size(min = 3, max = 12)
    String name;
}
