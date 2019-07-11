package com.dBuider.app.Controller;

import com.dBuider.app.Model.Form.OrderForm;
import com.dBuider.app.Model.Form.RegistrationForm;
import com.dBuider.app.Model.Order;
import com.dBuider.app.Repo.OrderRepo;
import com.dBuider.app.Repo.UserRepo;
import com.dBuider.app.Service.Interfaces.ToolsService;
import com.dBuider.app.Service.UserRepositoryUserDetailsService;
import com.dBuider.app.Validator.OrderValidator;
import com.dBuider.app.Validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController
{
    private final ToolsService toolsService;
    private final UserRepositoryUserDetailsService userService;
    private final OrderValidator orderValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder)
    {
        Object target = binder.getTarget();
        if (target==null)
            return;

        if (target.getClass() == OrderForm.class)
            binder.setValidator(orderValidator);
    }

    @GetMapping("/cart")
    public String cart(HttpSession session, Model model)
    {
        OrderForm form = new OrderForm();
        SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
        form.setDate(date.format(new Date()));
        String address = "";
        if (session.getAttribute("username") != null)
            address = userService.getUser((String)session
                    .getAttribute("username")).getAddress();
        form.setAddress(address);
        form.setFordays(1);
        model.addAttribute("form",form);
        return "order";
    }

    //todo
    @PostMapping
    public String processOrder(BindingResult result,
                               @Validated @ModelAttribute("form") OrderForm form,
                               Model model)
    {
        if (result.hasErrors())
        {
            return "order";
        }

        model.addAttribute("text","Ваш заказ принят на обработку, ожидайте звонка");
        return "redirect:/text";
    }
}
