package pl.polishstation.polishstationbackend.domain.fuel.fuelprice.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PrecisionValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Precision {
    String message() default "Price must have 2 decimal digits precision";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
