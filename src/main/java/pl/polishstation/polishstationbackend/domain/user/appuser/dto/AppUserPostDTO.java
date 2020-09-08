package pl.polishstation.polishstationbackend.domain.user.appuser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserPostDTO {
    private String username;
    private String email;
    private String password;
}
