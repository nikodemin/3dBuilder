package com.dBuider.app.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class UserController
{

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception
    {
        request.logout();
        return "login";
    }

    @GetMapping("/settings")
    public String settings(Model model)
    {

        //todo
        return "index";
    }
}
