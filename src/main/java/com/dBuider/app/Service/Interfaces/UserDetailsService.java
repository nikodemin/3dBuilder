package com.dBuider.app.Service.Interfaces;

import com.dBuider.app.Model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService
{
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    void saveUser(User user);
}
