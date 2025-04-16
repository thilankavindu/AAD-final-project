package com.example.finding_spare_part.controller;

    import com.example.finding_spare_part.dto.OrderDTO;
    import com.example.finding_spare_part.dto.ResponseDTO;
    import com.example.finding_spare_part.entity.OrderItem;
    import com.example.finding_spare_part.entity.Orders;
    import com.example.finding_spare_part.repo.OrderItemRepository;
    import com.example.finding_spare_part.service.OrderService;
    import com.example.finding_spare_part.util.VarList;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/orders")
    @CrossOrigin
    public class OrderController {

        private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

        @Autowired
        private OrderService orderService;

        @Autowired
        private OrderItemRepository orderItemRepository;

        @PostMapping("/place/{userId}")
        public ResponseEntity<OrderDTO> placeOrder(@PathVariable Long userId) {
            return ResponseEntity.ok(orderService.placeOrder(userId));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDTO> getOrderById(@PathVariable Long id) {
            try {
                logger.info("Fetching order with ID: {}", id);
                Orders order = orderService.getOrderById(id);
                return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Success", order));
            } catch (Exception e) {
                logger.error("Error retrieving order with ID {}: {}", id, e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
            }
        }

        // Add this alternative endpoint for fetching order items directly
        @GetMapping("/{id}/items")
        public ResponseEntity<ResponseDTO> getOrderItemsByOrderId(@PathVariable Long id) {
            try {
                logger.info("Fetching order items for order ID: {}", id);
                List<OrderItem> orderItems = orderItemRepository.findByOrderId(id);
                return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Success", orderItems));
            } catch (Exception e) {
                logger.error("Error retrieving order items for order ID {}: {}", id, e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
            }
        }

        @GetMapping("/{userId}")
        public ResponseEntity<ResponseDTO> getOrdersByUserId(@PathVariable Long userId) {
            try {
                logger.info("Fetching orders for user ID: {}", userId);
                List<Orders> orders = orderService.getOrdersByUserId(userId);
                return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Success", orders));
            } catch (Exception e) {
                logger.error("Error retrieving orders for user ID {}: {}", userId, e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
            }
        }

        @GetMapping("/all")
        public ResponseEntity<ResponseDTO> getAllOrders() {
            try {
                List<Orders> orders = orderService.getAllOrders();
                return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Success", orders));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
            }
        }




    }