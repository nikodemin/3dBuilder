package com.dBuider.app.Controller;

import com.dBuider.app.Config.PropertiesConfig;
import com.dBuider.app.Service.Interfaces.ToolsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
        return "main";
    }
}
