package com.niko.prokat.Controller.usual;

import com.niko.prokat.Model.dto.TulipDto;
import com.niko.prokat.Model.dto.UserDto;
import com.niko.prokat.Service.MessageBuilder;
import com.niko.prokat.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@PropertySource("classpath:server.properties")
public class UserController {
    private final UserService userService;
    private final JavaMailSender mailSender;
    private final MessageBuilder messageBuilder;

    @Value("${server.site.name}")
    private String siteName;

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
                                      Errors errors, Model model,
                                      Principal principal) throws MessagingException {
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

        TulipDto<Long, String> idAndToken = userService.saveUser(userDto);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        String htmlMsg = messageBuilder.buildRegistrationMessage(idAndToken.getFirst(),
                idAndToken.getSecond());
        message.setContent(htmlMsg, "text/html; charset=UTF-8");
        helper.setTo(userService.getUser(userDto.getUsername()).getEmail());
        helper.setSubject(siteName+" - регистрация");
        mailSender.send(message);

        model.addAttribute("text","Регистрация прошла успешно! Проверьте эл. почту");
        return "text";
    }

    @GetMapping("/activate-user/id/{id}/token/{token}")
    public String activateUser(@PathVariable Long id,
                               @PathVariable String token,
                               Model model){
        if (userService.activateUser(id,token)){
            model.addAttribute("text","Ваш аккаунт успешно активирован");
            return "text";
        } else {
            model.addAttribute("text","Неверный токен");
            return "text";
        }
    }
}
