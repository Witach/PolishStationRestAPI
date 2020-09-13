package pl.polishstation.polishstationbackend.domain.user.appuser.validation;

import pl.polishstation.polishstationbackend.domain.user.appuserrole.AppUserRole;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class IsDefaultValidator implements ConstraintValidator<IsDefault, List<AppUserRole>> {

   public void initialize(IsDefault constraint) {
   }

   public boolean isValid(List<AppUserRole> obj, ConstraintValidatorContext context) {
      return obj.stream()
              .anyMatch(
                      appUserRole -> appUserRole.getName().equals("USER")
              );
   }
}
