package com.example.finding_spare_part.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private Date orderDate;
    private String status;
    private List<OrderItemDTO> orderItems;
}