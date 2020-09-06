package pl.polishstation.polishstationbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import pl.polishstation.polishstationbackend.auth.JwtUtils;
import pl.polishstation.polishstationbackend.externalapis.googleapi.GoogleApiService;
import pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi.InfrastructureFuelStationService;

@Configuration
public class PropertyConfig {

    @Value("${jwt.secret}")
    private void setJwtSecret(String secret) {
        JwtUtils.SECRET = secret;
    }

    @Value("${apis.infrastructure-fuel-station.uri}")
    private void setUriForInfrastructureFuelStation(String uri) {
        InfrastructureFuelStationService.URI = uri;
    }

    @Value("${apis.infrastructure-fuel-station.file}")
    private void setJsonFilePathForInfrastructureFuelStation(String filePath) {
        InfrastructureFuelStationService.JSON_FILE_PATH = filePath;
    }
}
