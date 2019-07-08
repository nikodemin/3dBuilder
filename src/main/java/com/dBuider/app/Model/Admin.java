package com.dBuider.app.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "users")
public class Admin extends User
{
    private Admin()
    {
        super("","","",
                "","","","");
    }
    public Admin(String username, String password, String address,
                 String telnum, String email, String firstname, String lastname)
    {
        super(username, password, address, telnum, email, firstname, lastname);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")
                , new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
