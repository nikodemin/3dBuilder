package com.dBuider.app.Config;

import com.dBuider.app.Service.Interfaces.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/order/**")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**").access("permitAll")

                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")

                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("css/**");
    }
}
