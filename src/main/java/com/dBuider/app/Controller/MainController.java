package com.dBuider.app.Controller;

import com.dBuider.app.Config.PropertiesConfig;
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
public class MainController
{
    @Autowired
    private PropertiesConfig propertiesConfig;

    @GetMapping("/")
    public String main(Model model, Principal principal, HttpSession session)
    {
        if (principal != null)
        {
            session.setAttribute("username",principal.getName());
        }

        return "index";
    }

    @GetMapping("/portfolio")
    public String portfolio(Model model)
    {
        File dir = new File("src/main/resources/static/img/portfolio");
        List<String> images = new ArrayList<>();

        for (File file:dir.listFiles())
        {
            images.add("/img/portfolio/"+file.getName());
        }
        model.addAttribute("images", images);
        return "portfolio";
    }

}
