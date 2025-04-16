package com.example.finding_spare_part.service.impl;

import com.example.finding_spare_part.dto.OrderDTO;
import com.example.finding_spare_part.entity.OrderItem;
import com.example.finding_spare_part.entity.Orders;
import com.example.finding_spare_part.repo.OrderItemRepository;
import com.example.finding_spare_part.repo.OrderRepository;
import com.example.finding_spare_part.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;


    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Override
    public OrderDTO placeOrder(Long userId) {
        Orders order = new Orders();
        order.setUserId(userId);
        order.setOrderDate(new Date());
        order.setStatus("PLACED");
        orderRepository.save(order);
        return convertToDTO(order);
    }

    private OrderDTO convertToDTO(Orders order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUserId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setStatus(order.getStatus());
        return orderDTO;
    }

    @Override
    public Orders getOrderById(Long id) {
        try {
            logger.info("Retrieving order by ID: {}", id);
            Orders order = orderRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

            // Manually load order items to avoid LazyInitializationException
            List<OrderItem> items = orderItemRepository.findByOrderId(id);
            order.setOrderItems(items);

            return order;
        } catch (Exception e) {
            logger.error("Error in getOrderById: {}", e.getMessage(), e);
            throw new RuntimeException("Error retrieving order: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Orders> getOrdersByUserId(Long userId) {
        try {
            List<Orders> orders = orderRepository.findByUserId(userId);

            // Eagerly load order items to prevent LazyInitializationException
            if (orders != null) {
                for (Orders order : orders) {
                    List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
                    order.setOrderItems(items);
                }
            }

            return orders;
        } catch (Exception e) {
            // Log the exception
            logger.error("Error retrieving orders for user ID {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("Error retrieving orders: " + e.getMessage(), e);
        }
    }
    @Override
    public List<Orders> getAllOrders() {
        List<Orders> orders = orderRepository.findAll();

        // Load order items for each order to prevent LazyInitializationException
        if (orders != null) {
            for (Orders order : orders) {
                List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
                order.setOrderItems(items);
            }
        }

        return orders;
    }
}