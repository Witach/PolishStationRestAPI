package pl.polishstation.polishstationbackend.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRole;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRoleRepository;

import java.util.stream.Collectors;

import static pl.polishstation.polishstationbackend.domain.user.appuserrole.DefaultUserRoles.ROLES_NAMES;

@Component
public class RoleBootstrap implements CommandLineRunner {

    @Autowired
    AppUserRoleRepository appUserRoleRepository;

    @Override
    public void run(String... args) throws Exception {
        var userRolesList = ROLES_NAMES.stream()
                .map(AppUserRole::new)
                .collect(Collectors.toList());
        appUserRoleRepository.saveAll(userRolesList);
    }
}
