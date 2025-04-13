package com.example.finding_spare_part.service;

import com.example.finding_spare_part.dto.OrderItemDTO;
import java.util.List;

public interface OrderItemService {
    List<OrderItemDTO> getOrderItemsByOrderId(Long orderId);
    OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO);
}