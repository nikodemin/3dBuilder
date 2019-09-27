package com.niko.prokat.Service;

import com.niko.prokat.Model.dto.OrderDto;
import com.niko.prokat.Model.entity.Order;
import com.niko.prokat.Repo.OrderRepo;
import com.niko.prokat.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final DtoMapper mapper;

    public void addOrder(OrderDto orderDto, String username) {
        Order order = mapper.toOrder(orderDto);
        order.setUser(userRepo.findByUsername(username));
        orderRepo.save(order);
    }

    public List<OrderDto> getAllOrders() {
        return ((List<Order>) orderRepo.findAll()).stream()
                .map(mapper::toOrderDto).collect(Collectors.toList());
    }

    public List<OrderDto> getUncompletedOrders() {
        return (orderRepo.findByDoneIsFalse()).stream()
                .map(mapper::toOrderDto).collect(Collectors.toList());
    }

    public List<OrderDto> getUserOrders(String username) {
        return orderRepo.findByUser(userRepo.findByUsername(username)).stream()
                .map(mapper::toOrderDto).collect(Collectors.toList());
    }
}
