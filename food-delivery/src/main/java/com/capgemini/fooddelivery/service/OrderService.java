package com.capgemini.fooddelivery.service;

import com.capgemini.fooddelivery.entity.Order;
import com.capgemini.fooddelivery.repository.OrderRepository;
import com.capgemini.fooddelivery.repository.RestaurantRepository;
import com.capgemini.fooddelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public String placeOrder(Order order) {
        if (!userRepository.existsById(order.getUserId())) {
            return "ERROR: User not found with id " + order.getUserId();
        }
        if (!restaurantRepository.existsById(order.getRestaurantId())) {
            return "ERROR: Restaurant not found with id " + order.getRestaurantId();
        }
        orderRepository.save(order);
        return "Order placed successfully";
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public String deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            return "ERROR: Order not found";
        }
        orderRepository.deleteById(id);
        return "Order deleted successfully";
    }
}
