package pl.polishstation.polishstationbackend.auth.userdetails;

import org.springframework.security.core.GrantedAuthority;
import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRole;

public class AuthorityImpl implements GrantedAuthority {

    private String authority;

    public AuthorityImpl(AppUserRole appUserRole) {
        authority = appUserRole.getName();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
