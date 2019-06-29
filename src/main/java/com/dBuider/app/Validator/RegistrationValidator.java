package com.dBuider.app.Validator;

import com.dBuider.app.Model.RegistrationForm;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator
{
    @Override
    public boolean supports(Class<?> aClass)
    {
        return aClass == RegistrationForm.class;
    }

    @Override
    public void validate(Object o, Errors errors)
    {
        RegistrationForm form = (RegistrationForm) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.appUserForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUserForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirm", "NotEmpty.appUserForm.confirmPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.appUserForm.email");
    }
}
