package com.example.finding_spare_part.service.impl;

import com.example.finding_spare_part.dto.OrderItemDTO;
import com.example.finding_spare_part.entity.OrderItem;
import com.example.finding_spare_part.repo.OrderItemRepository;
import com.example.finding_spare_part.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItemDTO> getOrderItemsByOrderId(Long orderId) {
        try {
            return orderItemRepository.findByOrderId(orderId).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error getting order items: " + e.getMessage());
        }
    }

    @Override
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        try {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderItemDTO.getOrderId());
            orderItem.setProductId(orderItemDTO.getProductId());
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItemRepository.save(orderItem);
            return convertToDTO(orderItem);
        } catch (Exception e) {
            throw new RuntimeException("Error creating order item: " + e.getMessage());
        }
    }

    private OrderItemDTO convertToDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setOrderId(orderItem.getOrderId());
        orderItemDTO.setProductId(orderItem.getProductId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        return orderItemDTO;
    }
}