package com.niko.prokat.Service;

import com.niko.prokat.Model.dto.OrderDto;
import com.niko.prokat.Model.entity.Order;
import com.niko.prokat.Repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final DtoMapper mapper;

    public void addOrder(OrderDto orderDto) {
        orderRepo.save(mapper.toOrder(orderDto));
    }

    public List<OrderDto> getAllOrders() {
        return ((List<Order>) orderRepo.findAll()).stream()
                .map(o->mapper.toOrderDto(o)).collect(Collectors.toList());
    }

    public List<OrderDto> getUncompletedOrders() {
        return ((List<Order>) orderRepo.findAll()).stream()
                .filter(o -> !o.isDone())
                .map(o->mapper.toOrderDto(o)).collect(Collectors.toList());
    }
}
