package pl.polishstation.polishstationbackend.auth.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    String username;
    String password;
    Long userId;
    Boolean isVerified;
    List<AuthorityImpl> authorities;

    public UserDetailsImpl(AppUser appUser) {
        username = appUser.getEmail();
        password = appUser.getPassword();
        isVerified = appUser.getIsVerified();
        authorities = appUser.getAppUserRoles().stream()
                .map(AuthorityImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isVerified;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
