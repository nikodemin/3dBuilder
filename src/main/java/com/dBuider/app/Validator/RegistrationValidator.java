package com.dBuider.app.Validator;

import com.dBuider.app.Model.Form.RegistrationForm;
import com.dBuider.app.Repo.UserRepo;
import com.dBuider.app.Service.UserRepositoryUserDetailsService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator
{
    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private UserRepositoryUserDetailsService userService;

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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telnum", "NotEmpty.appUserForm.telNum");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.appUserForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.appUserForm.address");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "NotEmpty.appUserForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "NotEmpty.appUserForm.lastName");

        if (!this.emailValidator.isValid(form.getEmail()))
        {
            errors.rejectValue("email","Pattern.appUserForm.email");
        }
        else if (userService.getUserByEmail(form.getEmail()) != null)
        {
            errors.rejectValue("email","Duplicate.appUserForm.email");
        }

        if (!errors.hasFieldErrors("username"))
            if (userService.getUser(form.getUsername()) != null)
            {
                errors.rejectValue("username","Duplicate.appUserForm.username");
            }

        if (!errors.hasErrors())
            if (!form.getPassword().equals(form.getConfirm()))
            {
                errors.rejectValue("confirm","NotMatch.appUserForm.password");
            }

    }
}
