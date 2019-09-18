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

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registrationPage(Model model){
        model.addAttribute("userDto",new UserDto());
        return "register";
    }

    @PostMapping("/registration")
    public String processRegistration(@ModelAttribute @Valid UserDto userDto,
                                      Errors errors, Model model){
        if (userService.getUser(userDto.getUsername()) != null){
            errors.reject("username","Указанное имя пользователя уже занято");
        }
        if (userService.getUserByEmail(userDto.getEmail()) != null){
            errors.reject("email","Указанный адрес эл. почты уже занят");
        }
        if (!userDto.getPassword().equals(userDto.getConfirm())){
            errors.reject("confirm","Пароли не совпадают");
        }

        if (errors.hasErrors()) {
            return "register";
        }

        userService.saveUser(userDto);
        model.addAttribute("text","Регистрация прошла успешно! Проверьте эл. почту");
        return "text";
    }
}
