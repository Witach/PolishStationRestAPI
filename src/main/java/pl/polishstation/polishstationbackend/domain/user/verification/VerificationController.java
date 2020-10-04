package pl.polishstation.polishstationbackend.domain.user.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VerificationController {

    @Autowired
    RegisterService registerService;

    @RequestMapping("/verify/{token}")
    public String verifyUserAccount(@PathVariable String token) {
        registerService.verifyNewUser(token);
        return "verified";
    }
}
