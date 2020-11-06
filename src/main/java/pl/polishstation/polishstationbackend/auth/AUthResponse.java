package pl.polishstation.polishstationbackend.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AUthResponse {
    private String jwt;
    private String email;
    private Long id;

    public static AUthResponse instanceFromUserDetails(UserDetails userDetails) {
        return AUthResponse.builder()
                .jwt(JwtUtils.generateToken(userDetails))
                .email(userDetails.getUsername())
                .build();
    }
}
