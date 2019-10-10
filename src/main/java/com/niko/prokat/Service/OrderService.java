package com.niko.prokat.Service;

import com.niko.prokat.Model.dto.OrderDto;
import com.niko.prokat.Model.dto.TulipDto;
import com.niko.prokat.Model.entity.Order;
import com.niko.prokat.Model.enums.OrderStatus;
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

    public TulipDto<Integer,Integer> addOrder(OrderDto orderDto, String username) {
        Order order = mapper.toOrder(orderDto);
        order.setUser(userRepo.findByUsername(username));
        orderRepo.save(order);
        return new TulipDto<>(order.getTotal(),order.getPledge());
    }

    public List<OrderDto> getCompletedOrders() {
        return ((List<Order>) orderRepo.findByDoneIsTrue()).stream()
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

    public void changeOrderStatus(Long id, String newStatus) {
        Order order = orderRepo.findById(id).get();
        OrderStatus status;
        Boolean done = false;
        switch (newStatus){
            case "В обработке":
                status = OrderStatus.PROCESSING;
                break;
            case "Принят":
                status = OrderStatus.ACCEPTED;
                break;
            case "Отменён":
                status = OrderStatus.CANCELED;
                done = true;
                break;
            case "Завершён":
                status = OrderStatus.FINISHED;
                done = true;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + newStatus);
        }
        order.setStatus(status);
        order.setDone(done);
        orderRepo.save(order);
    }
}
