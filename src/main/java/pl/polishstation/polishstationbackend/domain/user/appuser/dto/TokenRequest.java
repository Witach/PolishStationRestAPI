package pl.polishstation.polishstationbackend.domain.user.appuser.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TokenRequest {
    @NotBlank
    String fireToken;
}
