package com.dBuider.app.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class MainController
{
    @GetMapping("/")
    public String main(Model model, Principal principal)
    {
        if (principal != null)
            model.addAttribute("username",principal.getName());

        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, Principal principal)
    {
        if (principal != null)
            model.addAttribute("username",principal.getName());

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception
    {
        request.logout();
        return "login";
    }

    @GetMapping("/settings")
    public String settings(Model model, Principal principal)
    {
        if (principal != null)
            model.addAttribute("username",principal.getName());

        //todo
        return "index";
    }
}
