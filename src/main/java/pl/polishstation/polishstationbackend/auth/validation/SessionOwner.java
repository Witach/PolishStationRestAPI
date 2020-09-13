package pl.polishstation.polishstationbackend.auth.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SessionOwnerValidatior.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionOwner {
    String message() default "User creating this object must be owner of session";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
