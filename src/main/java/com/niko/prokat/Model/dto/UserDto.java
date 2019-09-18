package com.niko.prokat.Model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    @NotBlank(message = "Имя пользователя не указано")
    private String username;

    @NotBlank(message = "Пароль не указан")
    private String password;

    @NotBlank(message = "Подтверждение пароля не указано")
    private String confirm;

    @NotBlank(message = "Адрес не указан")
    private String address;

    @NotBlank(message = "Номер телефона не указан")
    private String telnum;

    @NotBlank(message = "Адрес эл. почты не указан")
    @Email(message = "Неверный адрес эл. почты")
    private String email;

    @NotBlank(message = "Имя не указано")
    private String firstname;

    @NotBlank(message = "Фамилия не указана")
    private String lastname;
}
