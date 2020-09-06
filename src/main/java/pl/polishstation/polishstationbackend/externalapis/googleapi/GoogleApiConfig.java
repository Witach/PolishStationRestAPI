package pl.polishstation.polishstationbackend.externalapis.googleapi;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleApiConfig {

    @Bean
    public GeoApiContext geoApiContext(@Value("${apis.google-api.api-key}") String apiKey) {
        return new GeoApiContext.Builder()
                .apiKey(apiKey)
                .queryRateLimit(50)
                .build();
    }
}
