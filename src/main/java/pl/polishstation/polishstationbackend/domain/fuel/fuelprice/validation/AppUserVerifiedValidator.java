package pl.polishstation.polishstationbackend.domain.fuel.fuelprice.validation;

import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AppUserVerifiedValidator implements ConstraintValidator<Verified, AppUser> {
   public void initialize(Verified constraint) {
   }

   public boolean isValid(AppUser obj, ConstraintValidatorContext context) {
      return obj.getIsVerified();
   }
}
