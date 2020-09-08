package pl.polishstation.polishstationbackend.domain.user.verification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.polishstation.polishstationbackend.auth.JwtUtils;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserRepository;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserService;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTO;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTOMapper;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserDTOMapperImpl;
import pl.polishstation.polishstationbackend.domain.user.appuser.dto.AppUserPostDTO;

import javax.mail.MessagingException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static pl.polishstation.polishstationbackend.domain.user.appuserrole.UserRolesUtils.listOfAppUserRoles;

@ExtendWith(SpringExtension.class)
@SpringJUnitConfig
@ActiveProfiles("dev")
class RegisterServiceTest {

    private VerificationToken token;

    @Configuration
    static class Config {
        @Bean
        AppUserDTOMapperImpl appUserDTOMapper() {
            return new AppUserDTOMapperImpl();
        }
    }

    @InjectMocks
    private RegisterService registerService;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private EmailSender emailSender;

    @Mock
    private ITemplateEngine templateEngine;

    @Mock
    private AppUserService appUserService;

    @Mock
    private AppUserDTOMapper appUserDTOMapperMock;

    @Autowired
    private AppUserDTOMapperImpl appUserDTOMapper;

    private AppUserPostDTO appUserPostDTO;

    private AppUser appUser;

    @Captor
    private ArgumentCaptor<AppUser> appUserArgumentCaptor;

    @BeforeEach
    void setUp() {
        appUserPostDTO = AppUserPostDTO.builder()
                .email("witaszek98@wp.pl")
                .password("retsad123")
                .username("witaszek")
                .build();

        token = VerificationToken.builder()
                .appUser(appUser)
                .expiryDate(LocalDateTime.now().plusDays(10l))
                .token(JwtUtils.builder()
                        .subject("witaszek98@wp.pl")
                        .issuedAt(LocalDateTime.now())
                        .expiration(LocalDateTime.now().plusDays(10l))
                        .build())
                .build();

        appUser = AppUser.builder()
                .email("witaszek98@wp.pl")
                .password("{noop}retsad123")
                .username("witaszek")
                .isVerified(false)
                .appUserRoles(listOfAppUserRoles("USER"))
                .verificationToken(token)
                .build();

    }

    @Test
    void shouldSendMailWithToken() throws MessagingException {
        doReturn("HAPPY").when(templateEngine).process(anyString(), any());
        var appUser = appUserDTOMapper.convertIntoObject(appUserPostDTO);
        when(appUserDTOMapperMock.convertIntoObject(appUserPostDTO)).thenReturn(appUser);
        doNothing().when(emailSender).sendMail(anyString(), anyString());
        when(appUserService.addEntity(appUserPostDTO)).thenReturn(appUserDTOMapper.convertIntoDTO(appUser));

        var appUserResponse = registerService.registerNewUser(appUserPostDTO, "https://localhost:8080");

        verify(emailSender).sendMail(anyString(), anyString());
        assertEquals("witaszek98@wp.pl", appUserResponse.getEmail());
    }

    @Test
    void shouldVerifyNewUser() {
        when(appUserRepository.findAppUserByEmailEquals(eq("witaszek98@wp.pl")))
                .thenReturn(
                        Optional.of(appUser)
                );
        doReturn(new AppUserDTO()).when(appUserDTOMapperMock).convertIntoDTO(any());
        when(appUserRepository.save(appUser)).thenAnswer(i -> i.getArguments()[0]);
        registerService.verifyNewUser(token.getToken());

        verify(appUserRepository).save(appUserArgumentCaptor.capture());
        assertTrue(appUserArgumentCaptor.getValue().getIsVerified());
    }
}
