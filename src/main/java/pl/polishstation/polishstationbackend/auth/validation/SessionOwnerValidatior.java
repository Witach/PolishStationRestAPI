package pl.polishstation.polishstationbackend.auth.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.auth.userdetails.UserDetailsImpl;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class SessionOwnerValidatior implements ConstraintValidator<SessionOwner, Long> {

   @Autowired
   AppUserRepository appUserRepository;

   public void initialize(SessionOwner constraint) {
   }

   public boolean isValid(Long id, ConstraintValidatorContext context) {
      var appUserDetialsImpl =  (UserDetailsImpl)SecurityContextHolder.getContext()
              .getAuthentication()
              .getPrincipal();
      var appUser = appUserRepository.findAppUserByEmailEquals(appUserDetialsImpl.getUsername()).orElseThrow();
      return appUser.getId().equals(id);
   }
}
