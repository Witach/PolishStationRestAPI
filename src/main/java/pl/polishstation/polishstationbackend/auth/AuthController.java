package pl.polishstation.polishstationbackend.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import static pl.polishstation.polishstationbackend.auth.AUthResponse.instanceFromUserDetails;
import static pl.polishstation.polishstationbackend.utils.JsonWebTokens.authTokenFromAuthRequest;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping
    public String hello(){
        return "Hello world";
    }


    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) {
        authenticationManager.authenticate(authTokenFromAuthRequest(authenticationRequest));

        final var userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        return ResponseEntity.ok(instanceFromUserDetails(userDetails));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    void handleBadCredential(BadCredentialsException e) {
      e.printStackTrace();
    }
}
