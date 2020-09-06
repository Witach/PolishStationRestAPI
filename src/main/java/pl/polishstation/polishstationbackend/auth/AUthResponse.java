package pl.polishstation.polishstationbackend.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AUthResponse {
    private String jwt;

    public static AUthResponse instanceFromUserDetails(UserDetails userDetails) {
        return AUthResponse.builder()
                .jwt(JwtUtils.generateToken(userDetails))
                .build();
    }
}
