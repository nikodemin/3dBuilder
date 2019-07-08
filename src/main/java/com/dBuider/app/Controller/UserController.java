package com.dBuider.app.Controller;

import com.dBuider.app.Model.Form.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/login")
    public String loginPage(Model model)
    {
        model.addAttribute("form", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("form") LoginForm form)
    {
        System.out.println(form);
        return "login";
    }

    @GetMapping("/settings")
    public String settings(Model model)
    {

        //todo
        return "index";
    }
}
