package com.niko.prokat.Validator;

import com.niko.prokat.Model.dto.UserDto;
import com.niko.prokat.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
//todo
public class RegistrationValidator implements Validator {
    //private EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == UserDto.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto userDto = (UserDto) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.appUserForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUserForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirm", "NotEmpty.appUserForm.confirmPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telnum", "NotEmpty.appUserForm.telNum");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.appUserForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.appUserForm.address");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "NotEmpty.appUserForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "NotEmpty.appUserForm.lastName");

        if (false){//!this.emailValidator.isValid(user.getEmail())) {
            errors.rejectValue("email", "Pattern.appUserForm.email");
        } else if (userService.getUserByEmail(userDto.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.appUserForm.email");
        }

        if (!errors.hasFieldErrors("username"))
            if (userService.getUser(userDto.getUsername()) != null) {
                errors.rejectValue("username", "Duplicate.appUserForm.username");
            }

        if (!errors.hasErrors())
            if (!userDto.getPassword().equals(userDto.getConfirm())) {
                errors.rejectValue("confirm", "NotMatch.appUserForm.password");
            }

    }
}
