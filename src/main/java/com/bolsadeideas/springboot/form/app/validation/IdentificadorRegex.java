package com.bolsadeideas.springboot.form.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@Constraint(validatedBy = {})
@Retention(RUNTIME)
@Target({ FIELD, METHOD})
public @interface IdentificadorRegex {

    String message() default "Identificador inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
