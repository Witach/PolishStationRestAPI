package pl.polishstation.polishstationbackend.domain.user.appuser.dto;

import lombok.Data;

@Data
public class AppUserPostDTO {
    private String username;
    private String email;
    private String password;
}
