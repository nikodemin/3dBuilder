package com.dBuider.app.Service.Interfaces;

import com.dBuider.app.Model.Order;

import java.util.List;

public interface OrderService
{
    void addOrder(Order order);
    List<Order> getAllOrders();
    List<Order> getUncompletedOrders();
}
