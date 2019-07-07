package com.dBuider.app.Controller;

import com.dBuider.app.Model.Form.RegistrationForm;
import com.dBuider.app.Model.User;
import com.dBuider.app.Repo.UserRepo;
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
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationValidator registrationValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder)
    {
        Object target = binder.getTarget();
        if (target==null)
            return;

        System.out.println("TARGET="+target);

        if (target.getClass() == RegistrationForm.class)
            binder.setValidator(registrationValidator);
    }

    @GetMapping
    public String register(Model model)
    {
        model.addAttribute("form",new RegistrationForm());
        return "registration";
    }

    @PostMapping
    public String processRegistration(Model model,
                                      @Validated @ModelAttribute("form") RegistrationForm form,
                                      BindingResult result,
                                      final RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors())
            return "registration";

        User newUser = null;
        try
        {
            newUser = userRepo.save(form.toUser(passwordEncoder));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "registration";
        }

        redirectAttributes.addFlashAttribute("flashUser", newUser);
        return "redirect:/login";
    }
}
