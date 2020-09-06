package pl.polishstation.polishstationbackend.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.polishstation.polishstationbackend.auth.userdetails.UserDetailsImpl;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    private static String URL = null;
    RestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    AppUserRepository appUserRepository;
    AppUser appUser;

    @BeforeEach
    void setUp() {
        URL = String.format("http://localhost:%d", port);
        restTemplate = new RestTemplate();
        this.appUser = appUserRepository.findAppUserByEmailEquals("witaszek98@wp.pl");
    }
    @Test
    void hello() {
        assertThrows(HttpClientErrorException.Forbidden.class, () -> restTemplate.getForObject(URL + "/auth", String.class));
    }

    @Test
    void createAuthenticationToken() {
        var authRequest = AuthRequest.builder()
                .username(appUser.getEmail())
                .password(appUser.getPassword())
                .build();

         var password = extractPassword(authRequest);
         authRequest.setPassword(password);

        var authResponse = restTemplate.postForObject(URL + "/auth", authRequest, AUthResponse.class);
        assertNotNull(authResponse);
        assertNotNull(authResponse.getJwt());
        assertEquals(appUser.getEmail(), JwtUtils.extractUsername(authResponse.getJwt()));
    }

    private String extractPassword(AuthRequest authRequest) {
        return authRequest.getPassword().split("}")[1];
    }

    @Test
    void shouldAcceptRequestWithJWT() {
        final var headers = new HttpHeaders();
        var details = new UserDetailsImpl(appUser);
        headers.set("Authorization", "Bearer " + JwtUtils.generateToken(details));
        final var entity = new HttpEntity<String>(headers);
        var response = restTemplate.exchange(URL + "/auth", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hello world", response.getBody());
    }
}
