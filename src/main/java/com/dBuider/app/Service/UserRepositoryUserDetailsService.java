package com.dBuider.app.Service;

import com.dBuider.app.Config.PropertiesConfig;
import com.dBuider.app.Model.User;
import com.dBuider.app.Repo.UserRepo;
import com.dBuider.app.Service.Interfaces.UserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@RequiredArgsConstructor
@Service
@Log4j2
@SuppressWarnings("deprecation")
public class UserRepositoryUserDetailsService implements UserDetailsService
{
    private final UserRepo userRepo;
    private final PropertiesConfig config;


    @Bean
    public PasswordEncoder encoder()
    {
        return new StandardPasswordEncoder(config.getSecret());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepo.findByUsername(username);
        if (user != null)
            return user;

        throw new UsernameNotFoundException("User "+username+" not found in DB");
    }

    @Override
    public void saveUser(User user)
    {
        if(userRepo.findByUsername(user.getUsername()) == null &&
        userRepo.findByEmail(user.getEmail())==null)
        {
            try
            {
                Field password = User.class.getDeclaredField("password");
                password.setAccessible(true);
                password.set(user,encoder().encode(config.getSalt1()
                        + user.getPassword() + config.getSalt2()));
                userRepo.save(user);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
            log.error("User "+user.getUsername()+" already exists!");
    }
}
