package com.dBuider.app.Controller.REST;

import com.dBuider.app.Model.Order;
import com.dBuider.app.Service.Interfaces.ToolsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/addtool")
@RequiredArgsConstructor
public class AddToolController
{
    private final ToolsService toolsService;

    @PostMapping("/{id}")
    public Boolean addTool(@PathVariable(value = "id") String id,
                          HttpSession session)
    {
        Order order = (Order)session.getAttribute("order");
        if (toolsService.findToolById(id) != null)
        {
            order.getTools().add(toolsService.findToolById(id));
            session.setAttribute("order",order);
            return true;
        }

        return false;
    }
}
