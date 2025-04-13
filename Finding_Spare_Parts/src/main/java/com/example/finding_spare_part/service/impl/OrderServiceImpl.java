package com.example.finding_spare_part.service.impl;

import com.example.finding_spare_part.dto.OrderDTO;
import com.example.finding_spare_part.entity.Orders;
import com.example.finding_spare_part.repo.OrderRepository;
import com.example.finding_spare_part.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderDTO placeOrder(Long userId) {
        Orders order = new Orders();
        order.setUserId(userId);
        order.setOrderDate(new Date());
        order.setStatus("PLACED");
        orderRepository.save(order);
        return convertToDTO(order);
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(Orders order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUserId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setStatus(order.getStatus());
        return orderDTO;
    }
}