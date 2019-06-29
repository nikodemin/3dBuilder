package com.dBuider.app.Service;

import com.dBuider.app.Model.User;
import com.dBuider.app.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserRepositoryUserDetailsService implements  UserDetailsService
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
}
