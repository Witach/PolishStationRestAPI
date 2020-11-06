package pl.polishstation.polishstationbackend.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserRepository;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTO;

import static pl.polishstation.polishstationbackend.auth.AUthResponse.instanceFromUserDetails;
import static pl.polishstation.polishstationbackend.utils.JsonWebTokens.authTokenFromAuthRequest;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping
    public String hello(){
        return "Hello world";
    }


    @PostMapping
    public ResponseEntity<AUthResponse> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) {
        authenticationManager.authenticate(authTokenFromAuthRequest(authenticationRequest));

        final var userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final var appUser = appUserRepository.findAppUserByEmailEquals(authenticationRequest.getUsername()).orElseThrow();
        var res = instanceFromUserDetails(userDetails);
        res.setId(appUser.getId());
        return ResponseEntity.ok(res);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    void handleBadCredential(BadCredentialsException e) {
      e.printStackTrace();
    }
}
