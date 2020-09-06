package pl.polishstation.polishstationbackend.domain.user.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.polishstation.polishstationbackend.auth.JwtUtils;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserRepository;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserService;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTOMapper;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserPostDTO;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

@Service
public class RegisterService {
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppUserDTOMapper appUserDTOMapper;

    @Value("${register.expiration-date-days:}")
    private Long exporationDateDays;

    AppUserDTO registerNewUser(AppUserPostDTO appUserPostDTO, String url) throws MessagingException {
        var appUser = appUserDTOMapper.convertIntoObject(appUserPostDTO);
        var expirationDate = calculateExpirationDate(LocalDateTime.now());
        var token = VerificationToken.builder()
                .appUser(appUser)
                .expiryDate(expirationDate)
                .token(generateToken(expirationDate, appUser.getEmail()))
                .build();
        appUser.setVerificationToken(token);
        var emailContent = generateRegistrationEmailContext(token.getToken(), url);
        emailSender.sendMail(appUser.getEmail(), emailContent);
        return appUserService.addEntity(appUserPostDTO);
    }

    private String generateToken(LocalDateTime expiration, String userName) {
        return JwtUtils.builder()
                .expiration(expiration)
                .issuedAt(LocalDateTime.now())
                .subject(userName)
                .build();
    }

    private String generateRegistrationEmailContext(String token, String url) {
        var context = new Context();
        context.setVariable("header", "PolishStation");
        context.setVariable("title", "Verify your account");
        context.setVariable("description", url + "/app-user/verify/" + token);
        return templateEngine.process("template", context);
    }

    private LocalDateTime calculateExpirationDate(LocalDateTime from) {
        return from.plusDays(exporationDateDays);
    }
}
