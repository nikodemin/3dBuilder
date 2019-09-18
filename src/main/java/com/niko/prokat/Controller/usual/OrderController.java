package com.niko.prokat.Controller.usual;

import com.niko.prokat.Model.dto.OrderDto;
import com.niko.prokat.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/cart")
    public String getOrderPage(Model model){
        model.addAttribute("orderDto",new OrderDto());
        return "order";
    }

    @PostMapping
    public String processOrder(@ModelAttribute @Valid OrderDto orderDto,
                               Errors errors,
                               Model model){
        if (orderDto.getTools().isEmpty()){
            errors.reject("tools", "В корзине нет инструментов");
        }

        if (errors.hasErrors()){
            return "order";
        }

        orderService.addOrder(orderDto);
        model.addAttribute("text","Ваш заказ принят. Ожидайте звонка");
        return "text";
    }
}
