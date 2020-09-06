package pl.polishstation.polishstationbackend.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileterAuthData {
    private String username = null;
    private String jwt = null;

    public FileterAuthData(String jwt) {
        this.username = JwtUtils.extractUsername(jwt);
        this.jwt = jwt;
    }
}
