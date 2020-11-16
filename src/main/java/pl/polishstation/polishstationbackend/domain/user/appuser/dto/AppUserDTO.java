package pl.polishstation.polishstationbackend.domain.user.appuser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserDTO {
    private String username;
    private String email;
    private Long id;
    private List<String> appUserRoles;
    private Boolean isVerified;
    private Long amountOfPoints;
}
