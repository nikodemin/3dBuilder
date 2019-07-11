package com.dBuider.app.Controller;

import com.dBuider.app.Service.Interfaces.ToolsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController
{
    private final ToolsService toolsService;

    @GetMapping("/")
    public String main(Model model, Principal principal, HttpSession session)
    {
        if (principal != null)
        {
            session.setAttribute("username",principal.getName());
        }
        model.addAttribute("topTools",toolsService.getTopTools());
        return "main";
    }
}
