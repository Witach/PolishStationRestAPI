package pl.polishstation.polishstationbackend.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserRepository;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRoleRepository;

import java.util.List;

@Order(3)
@Component
public class DefaultUserPersister implements CommandLineRunner {

    AppUserRoleRepository appUserRoleRepository;
    AppUserRepository appUserRepository;

    @Autowired
    public DefaultUserPersister(AppUserRoleRepository appUserRoleRepository, AppUserRepository appUserRepository) {
        this.appUserRoleRepository = appUserRoleRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        var defaultRole = appUserRoleRepository.getDefaultUserRole().orElseThrow();
        var defaultUser = AppUser.builder()
                .email("witaszek98@wp.pl")
                .password("{noop}#Ptys123")
                .username("witszek98")
                .isVerified(true)
                .appUserRoles(List.of(defaultRole))
                .build();
        appUserRepository.save(defaultUser);
    }
}
