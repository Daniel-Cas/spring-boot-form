package com.bolsadeideas.springboot.form.app.validation;

import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UsuarioValidador implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return Usuario.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Usuario usuario = (Usuario)target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "NotEmpty.user.username");

        if( !usuario.getIdentificador().matches("[0-9]{3}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}") ){
            errors.rejectValue("identificador", "pattern.user.identificador");
        }

    }
}
