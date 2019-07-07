package com.dBuider.app.Model.Form;

import com.dBuider.app.Config.PropertiesConfig;
import com.dBuider.app.Model.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm
{
    private String username;
    private String password;
    private String confirm;
    private String telnum;
    private String email;
    private String address;
    private String firstname;
    private String lastname;
    @Autowired
    private PropertiesConfig config;

    public User toUser(PasswordEncoder passwordEncoder)
    {
        return new User(username,passwordEncoder.
                encode(config.getSalt1()+password+config.getSalt2()),
                address, telnum, email,firstname,lastname);
    }
}
