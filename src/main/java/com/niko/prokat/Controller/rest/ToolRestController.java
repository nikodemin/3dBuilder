package com.niko.prokat.Controller.rest;

import com.niko.prokat.Model.dto.OrderDto;
import com.niko.prokat.Model.dto.ToolDto;
import com.niko.prokat.Model.dto.TulipDto;
import com.niko.prokat.Service.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ToolRestController {
    private final ToolService toolService;

    @PostMapping("/tool/{id}")
    public Integer addToolToCart(@PathVariable Long id, HttpSession session){
        OrderDto order =  (OrderDto) session.getAttribute("order");
        order.getTools().add(toolService.findToolById(id));
        session.setAttribute("order",order);

        return order.getUniqTools().size();
    }

    @DeleteMapping("/tool/{id}")
    public Integer deleteToolFromCart(@PathVariable Long id, HttpSession session){
        OrderDto order =  (OrderDto) session.getAttribute("order");
        final boolean[] firstDel = {true};
        order.getTools().removeIf(t->{
            if (firstDel[0] && t.getId().equals(id)){
                firstDel[0] = false;
                return true;
            }
            return false;
        });
        session.setAttribute("order",order);

        return order.getUniqTools().size();
    }

    @GetMapping("/cart-tools")
    public List<TulipDto<ToolDto,Integer>> getCartTools(HttpSession session){
        return ((OrderDto) session.getAttribute("order")).getUniqTools();
    }
}
