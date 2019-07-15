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
@RequiredArgsConstructor
public class OrderMngmntController
{
    private final ToolsService toolsService;

    @PostMapping("/addtool/{id}")
    public Integer addTool(@PathVariable(value = "id") String id,
                          HttpSession session)
    {
        Order order = (Order)session.getAttribute("order");
        if (order!= null)
        {
            if(toolsService.findToolById(id) != null)
            {
                order.getTools().add(toolsService.findToolById(id));
                session.setAttribute("order", order);
            }
            return order.getUniqtools().size();
        }

        return 0;
    }

    @PostMapping("/deltool/{id}")
    public Integer delTool(@PathVariable(value = "id") String id,
                        HttpSession session)
    {
        Order order = (Order)session.getAttribute("order");
        if (order!= null)
        {
            order.getTools().remove(toolsService.findToolById(id));
            return order.getUniqtools().size();
        }
        return 0;
    }
}
