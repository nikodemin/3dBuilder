package com.niko.prokat.Controller.usual;

import com.niko.prokat.Model.dto.OrderDto;
import com.niko.prokat.Model.dto.TulipDto;
import com.niko.prokat.Model.dto.UserDto;
import com.niko.prokat.Model.enums.OrderStatus;
import com.niko.prokat.Service.MessageBuilder;
import com.niko.prokat.Service.OrderService;
import com.niko.prokat.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final JavaMailSender mailSender;
    private final MessageBuilder messageBuilder;

    @GetMapping("/order/cart")
    public String getOrderPage(Model model,Principal principal){
        OrderDto orderDto = new OrderDto();
        UserDto user = userService.getUser(principal.getName());
        orderDto.setAddress(user.getAddress());
        model.addAttribute("orderDto",orderDto);
        return "order";
    }

    @GetMapping("/order/all")
    public String getOrdersPage(Model model, Principal principal){
        model.addAttribute("orders",orderService.getUserOrders(principal.getName()));
        return "orders";
    }

    @PostMapping("/order")
    public String processOrder(@ModelAttribute @Valid OrderDto orderDto,
                               Errors errors,
                               HttpSession session,
                               Model model, Principal principal) throws MessagingException {
        orderDto.setTools(((OrderDto) session.getAttribute("order")).getTools());
        if (orderDto.getTools().isEmpty()){
            errors.rejectValue("tools", "","В корзине нет инструментов");
        }

        if (errors.hasErrors()){
            return "order";
        }

        TulipDto<Integer, Integer> totalAndPledge =
                orderService.addOrder(orderDto, principal.getName());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        String htmlMsg = messageBuilder.buildOrderMessage(orderDto,principal.getName(),
                totalAndPledge.getFirst(),totalAndPledge.getSecond());
        message.setContent(htmlMsg, "text/html; charset=UTF-8");
        helper.setTo(userService.getUser(principal.getName()).getEmail());
        helper.setSubject("Аренда - новый заказ");
        mailSender.send(message);

        session.setAttribute("order",new OrderDto());

        model.addAttribute("text","Ваш заказ принят. Ожидайте звонка");
        return "text";
    }

    @GetMapping("/admin/orders")
    public String getOrdersPage(Model model){
        model.addAttribute("uncompletedOrders",orderService.getUncompletedOrders());
        model.addAttribute("completedOrders",orderService.getCompletedOrders());
        model.addAttribute("listStatus", OrderStatus.values());
        return "adminOrders";
    }
}
