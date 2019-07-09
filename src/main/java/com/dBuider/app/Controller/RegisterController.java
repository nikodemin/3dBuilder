package com.dBuider.app.Controller;

import com.dBuider.app.Model.Form.RegistrationForm;
import com.dBuider.app.Model.User;
import com.dBuider.app.Repo.UserRepo;
import com.dBuider.app.Service.UserRepositoryUserDetailsService;
import com.dBuider.app.Validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController
{
    private final UserRepositoryUserDetailsService userService;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationValidator registrationValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder)
    {
        Object target = binder.getTarget();
        if (target==null)
            return;

        if (target.getClass() == RegistrationForm.class)
            binder.setValidator(registrationValidator);
    }

    @GetMapping
    public String register(Model model)
    {
        model.addAttribute("form",new RegistrationForm());
        return "register";
    }

    @PostMapping
    public String processRegistration(Model model,
                                      @Validated @ModelAttribute("form") RegistrationForm form,
                                      BindingResult result)
    {
        if (result.hasErrors())
            return "register";

        try
        {
            userService.saveUser(form.toUser(passwordEncoder));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "register";
        }

        return "redirect:/login";
    }
}
