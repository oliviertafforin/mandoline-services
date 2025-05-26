package fr.oliweb.mandoline.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.UUID;

public class ValidUUIDValidator implements ConstraintValidator<ValidUUID, Object> {

    private boolean nullable;

    @Override
    public void initialize(ValidUUID annotation) {
        this.nullable = annotation.nullable();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return nullable;
        }

        if (value instanceof UUID) {
            return true;
        }

        if (value instanceof String stringValue && !stringValue.isBlank()) {
            try {
                UUID.fromString(stringValue);
                return true;
            } catch (IllegalArgumentException ignored) {
            }
        }

        return false;
    }
}
