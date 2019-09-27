package com.niko.prokat.Controller.usual;

import com.niko.prokat.Model.dto.UserDto;
import com.niko.prokat.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/settings")
    public String getSettingsPage(Principal principal,
                                  Model model){
        model.addAttribute("userDto",userService.getUser(principal.getName()));
        return "settings";
    }

    @PostMapping("/settings")
    public String processUserUpdate(@ModelAttribute @Valid UserDto userDto,
                                    Errors errors, Model model, Principal principal){
        UserDto oldUser = userService.getUser(principal.getName());

        if (!oldUser.getUsername().equals(userDto.getUsername()) &&
                userService.getUser(userDto.getUsername()) != null){
            errors.rejectValue("username", "","Указанное имя пользователя уже занято");
        }
        if (!oldUser.getEmail().equals(userDto.getEmail()) &&
                userService.getUserByEmail(userDto.getEmail()) != null){
            errors.rejectValue("email","","Указанный адрес эл. почты уже занят");
        }
        if (!userDto.getPassword().equals(userDto.getConfirm())){
            errors.rejectValue("confirm","","Пароли не совпадают");
        }

        if (errors.hasErrors()) {
            return "settings";
        }

        userService.updateUser(userDto, principal.getName());
        model.addAttribute("text","Настройки успешно изменены!");
        return "text";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model){
        model.addAttribute("userDto",new UserDto());
        return "register";
    }

    @PostMapping("/registration")
    public String processRegistration(@ModelAttribute @Valid UserDto userDto,
                                      Errors errors, Model model){
        if (userService.getUser(userDto.getUsername()) != null){
            errors.rejectValue("username", "","Указанное имя пользователя уже занято");
        }
        if (userService.getUserByEmail(userDto.getEmail()) != null){
            errors.rejectValue("email","","Указанный адрес эл. почты уже занят");
        }
        if (!userDto.getPassword().equals(userDto.getConfirm())){
            errors.rejectValue("confirm","","Пароли не совпадают");
        }

        if (errors.hasErrors()) {
            return "register";
        }

        userService.saveUser(userDto);
        model.addAttribute("text","Регистрация прошла успешно! Проверьте эл. почту");
        return "text";
    }
}
