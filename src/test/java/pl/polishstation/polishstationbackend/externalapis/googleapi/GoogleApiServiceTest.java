package pl.polishstation.polishstationbackend.externalapis.googleapi;

import com.google.maps.errors.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.polishstation.polishstationbackend.domain.localization.Localization;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GoogleApiServiceTest {

    @Autowired
    GoogleApiService googleApiService;
    Localization localization;

    @BeforeEach
    void setUp() {
        localization = Localization.builder()
                .name("Łódź")
                .province("łódzkie")
                .postalCode("05-622")
                .number("13")
                .street("ul. Piotrkowska")
                .build();
    }

    @Test
    void getLocationOfLocalization() throws InterruptedException, ApiException, IOException {
        var location = googleApiService.getLocationOfLocalization(localization);
        assertNotNull(location);
        assertEquals(51.774519, location.get_long());
        assertEquals(19.4548889, location.getLat());
    }
}
