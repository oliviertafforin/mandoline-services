package fr.oliweb.mandoline.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidUUIDValidator.class)
@Documented
public @interface ValidUUID {
    String message() default "UUID invalide ou mal formaté";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // Permet de rendre la validation optionnelle (accepte null)
    boolean nullable() default false;
}