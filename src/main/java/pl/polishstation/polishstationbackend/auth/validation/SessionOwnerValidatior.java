package pl.polishstation.polishstationbackend.auth.validation;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.polishstation.polishstationbackend.auth.userdetails.UserDetailsImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class SessionOwnerValidatior implements ConstraintValidator<SessionOwner, Long> {

   public void initialize(SessionOwner constraint) {
   }

   public boolean isValid(Long id, ConstraintValidatorContext context) {
      var appUserDetialsImpl =  (UserDetailsImpl)SecurityContextHolder.getContext()
              .getAuthentication()
              .getDetails();
      return appUserDetialsImpl.getUserId().equals(id);
   }
}
