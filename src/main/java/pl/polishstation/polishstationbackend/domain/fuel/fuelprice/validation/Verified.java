package pl.polishstation.polishstationbackend.domain.fuel.fuelprice.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AppUserVerifiedValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Verified {
    String message() default "User must be verified";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
