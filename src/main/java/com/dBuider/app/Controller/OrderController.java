package com.dBuider.app.Controller;

import com.dBuider.app.Model.Form.OrderForm;
import com.dBuider.app.Model.Order;
import com.dBuider.app.Model.Tool;
import com.dBuider.app.Model.User;
import com.dBuider.app.Service.UserRepositoryUserDetailsService;
import com.dBuider.app.Validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController
{
    private final UserRepositoryUserDetailsService userService;
    private final OrderValidator orderValidator;
    private final JavaMailSender emailSender;

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

    @PostMapping
    public String processOrder(@Validated @ModelAttribute("form") OrderForm form,
                               RedirectAttributes attr,
                               HttpSession session,
                               BindingResult result) throws MessagingException
    {
        if (result.hasErrors())
        {
            return "order";
        }

        User user = userService.getUser((String)session.getAttribute("username"));
        //todo
        /*MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        String htmlMsg = "<h3>Привет мир!</h3>";

        message.setContent(htmlMsg,"text/html");
        helper.setTo("niko.demin@gmail.com");
        helper.setSubject("Test send HTML email");
        emailSender.send(message);*/


        session.setAttribute("order", new Order(user.getAddress(),new ArrayList<Tool>(),
                user, new Date(), 1, false));

        attr.addFlashAttribute("text","Ваш заказ принят на обработку, ожидайте звонка");
        return "redirect:/text";
    }
}
