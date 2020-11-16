package pl.polishstation.polishstationbackend.bootstrap.fake;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRole;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRoleRepository;
import pl.polishstation.polishstationbackend.domain.user.verification.VerificationToken;
import pl.polishstation.polishstationbackend.domain.user.verification.VerificationTokenRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Profile("dev")
public class AppUserFakeData {
    static final List<String> ROLE_NAMES = Collections.unmodifiableList(List.of("FAKE_ADMIN", "FAKE_USER"));

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    FakeValuesService fakeValuesService;
    @Autowired
    AppUserRoleRepository appUserRoleRepository;
    @Autowired
    private Random random;
    @Autowired
    private Faker faker;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    private static final String PASSWORD_ENCRYPTION_TAG = "{noop}";


    public List<AppUser> fakeAppUsers(List<AppUserRole> appUserRoles) {
        return IntStream.range(0, 5)
                .mapToObj(v -> fakeAppUserOfType(appUserRoles.get(random.nextInt(appUserRoles.size() - 1))))
                .collect(Collectors.toList());
    }

    public AppUser fakeAppUserOfType(AppUserRole appUserRole) {
        var token = fakeVerificationToken();
        var defaultUserRole = appUserRoleRepository.getDefaultUserRole().orElseThrow();
        var appUser = AppUser.builder()
                .email(fakeEmail())
                .appUserRoles(List.of(appUserRole, defaultUserRole))
                .username(fakeUserName())
                .verificationToken(token)
                .isVerified(true)
                .password(PASSWORD_ENCRYPTION_TAG + faker.animal().name())
                .amountOfPoints(0L)
                .build();
        token.setAppUser(appUser);
        return appUser;
    }

    private VerificationToken fakeVerificationToken() {
        return VerificationToken.builder()
                .token("eeeeeeeee")
                .expiryDate(LocalDateTime.now().plusDays(30))
                .build();
    }

    public String fakeEmail() {
        return fakeValuesService.bothify("????##@gmail.com");
    }

    public String fakeUserName() {
        return fakeValuesService.bothify("????##");
    }

    public List<AppUserRole> fakeAppUserRoles() {
        return ROLE_NAMES.stream()
                .map(AppUserRole::new)
                .collect(Collectors.toList());
    }
}
