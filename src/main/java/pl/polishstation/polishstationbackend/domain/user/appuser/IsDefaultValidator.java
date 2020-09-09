package pl.polishstation.polishstationbackend.domain.user.appuser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsDefaultValidator implements ConstraintValidator<IsDefault, AppUser> {
   public void initialize(IsDefault constraint) {
   }

   public boolean isValid(AppUser obj, ConstraintValidatorContext context) {
      return obj.getAppUserRoles()
              .stream()
              .anyMatch(
                      appUserRole -> appUserRole.getName().equals("USER")
              );
   }
}
