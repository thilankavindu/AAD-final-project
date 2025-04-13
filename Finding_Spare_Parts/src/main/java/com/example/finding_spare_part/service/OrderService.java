package com.example.finding_spare_part.service;

import com.example.finding_spare_part.dto.OrderDTO;
import java.util.List;

public interface OrderService {
    OrderDTO placeOrder(Long userId);
    List<OrderDTO> getOrdersByUserId(Long userId);
}