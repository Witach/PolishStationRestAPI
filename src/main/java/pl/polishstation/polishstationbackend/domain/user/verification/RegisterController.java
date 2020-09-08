package pl.polishstation.polishstationbackend.domain.user.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserPostDTO;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import static pl.polishstation.polishstationbackend.utils.ExtractAppUrl.extractAppUrl;

@RestController
@RequestMapping("/app-user")
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @PostMapping("/register")
    public AppUserDTO registerUserAccount(@RequestBody AppUserPostDTO appUserPostDTO, HttpServletRequest request) throws MessagingException {
        var appUrl = extractAppUrl(request);
        return registerService.registerNewUser(appUserPostDTO, appUrl);
    }

    @GetMapping("/verify/{token}")
    public AppUserDTO verifyUserAccount(@PathVariable String token) {
        return registerService.verifyNewUser(token);
    }

    @ExceptionHandler(MessagingException.class)
    ResponseEntity<?> handleMessagingException(MessagingException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().build();
    }
}
