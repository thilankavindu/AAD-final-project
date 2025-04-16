package com.example.finding_spare_part.service;

import com.example.finding_spare_part.dto.OrderDTO;
import com.example.finding_spare_part.entity.Orders;

import java.util.List;

public interface OrderService {
    OrderDTO placeOrder(Long userId);
    List<Orders> getOrdersByUserId(Long userId);

    Orders getOrderById(Long id);

    List<Orders> getAllOrders();
}