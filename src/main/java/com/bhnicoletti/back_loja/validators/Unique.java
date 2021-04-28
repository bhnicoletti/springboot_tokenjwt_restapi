package com.bhnicoletti.back_loja.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueValidator.class)

public @interface Unique {

    String message() default "Dado já cadastrado";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
