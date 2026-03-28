package com.capgemini.fooddelivery.controller;

import com.capgemini.fooddelivery.dto.ApiResponse;
import com.capgemini.fooddelivery.entity.Order;
import com.capgemini.fooddelivery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse> placeOrder(@RequestBody Order order) {
        String result = orderService.placeOrder(order);
        if (result.startsWith("ERROR")) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("ERROR", result.replace("ERROR: ", "")));
        }
        return ResponseEntity.ok(new ApiResponse("SUCCESS", result));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("ERROR", "Order not found with id " + id));
        }
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long id) {
        String result = orderService.deleteOrder(id);
        if (result.startsWith("ERROR")) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("ERROR", result.replace("ERROR: ", "")));
        }
        return ResponseEntity.ok(new ApiResponse("SUCCESS", result));
    }
}
