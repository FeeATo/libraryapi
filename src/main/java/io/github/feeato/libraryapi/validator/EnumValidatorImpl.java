package io.github.feeato.libraryapi.validator;

import io.github.feeato.libraryapi.annotation.EnumValidator;
import io.github.feeato.libraryapi.model.dto.LivroDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    List<String> valueList = null;

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        valueList = new ArrayList<>();
        Class<? extends Enum<?>> enumClasses = constraintAnnotation.enumClazz();
        Enum<?>[] enumConstants = enumClasses.getEnumConstants();
        for (Enum<?> enumConstant : enumConstants) {
            valueList.add(enumConstant.name().toUpperCase());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return valueList.contains(value.toUpperCase());
    }
}
