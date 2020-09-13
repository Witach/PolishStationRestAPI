package pl.polishstation.polishstationbackend.domain.user.appuser.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsDefaultValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsDefault {
    String message() default "User must have at least USER role";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
