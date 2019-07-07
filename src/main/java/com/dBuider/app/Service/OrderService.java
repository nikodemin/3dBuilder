package com.dBuider.app.Service;

import com.dBuider.app.Model.Order;
import com.dBuider.app.Repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements com.dBuider.app.Service.Interfaces.OrderService
{
    private final OrderRepo orderRepo;

    @Override
    public void addOrder(Order order)
    {
        orderRepo.save(order);
    }

    @Override
    public List<Order> getAllOrders()
    {
        return (List<Order>)orderRepo.findAll();
    }

    @Override
    public List<Order> getUncompletedOrders()
    {
        return getAllOrders().stream().filter(o->!o.isDone())
                .collect(Collectors.toList());
    }
}
