package com.dBuider.app.Service;

import com.dBuider.app.Model.User;
import com.dBuider.app.Repo.UserRepo;
import com.dBuider.app.Service.Interfaces.UserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserRepositoryUserDetailsService implements UserDetailsService
{
    private final UserRepo userRepo;

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
            userRepo.save(user);
        else
            log.error("User "+user.getUsername()+" already exists!");
    }
}
