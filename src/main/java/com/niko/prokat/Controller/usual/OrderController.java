package com.niko.prokat.Controller.usual;

import com.niko.prokat.Model.dto.OrderDto;
import com.niko.prokat.Model.dto.UserDto;
import com.niko.prokat.Service.OrderService;
import com.niko.prokat.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/cart")
    public String getOrderPage(Model model,Principal principal){
        OrderDto orderDto = new OrderDto();
        UserDto user = userService.getUser(principal.getName());
        orderDto.setAddress(user.getAddress());
        model.addAttribute("orderDto",orderDto);
        return "order";
    }

    @GetMapping("/all")
    public String getOrdersPage(Model model, Principal principal){
        model.addAttribute("orders",orderService.getUserOrders(principal.getName()));
        return "orders";
    }

    @PostMapping
    public String processOrder(@ModelAttribute @Valid OrderDto orderDto,
                               Errors errors,
                               HttpSession session,
                               Model model, Principal principal){
        orderDto.setTools(((OrderDto) session.getAttribute("order")).getTools());
        if (orderDto.getTools().isEmpty()){
            errors.rejectValue("tools", "В корзине нет инструментов");
        }

        if (errors.hasErrors()){
            return "order";
        }

        orderService.addOrder(orderDto, principal.getName());
        model.addAttribute("text","Ваш заказ принят. Ожидайте звонка");
        return "text";
    }
}
