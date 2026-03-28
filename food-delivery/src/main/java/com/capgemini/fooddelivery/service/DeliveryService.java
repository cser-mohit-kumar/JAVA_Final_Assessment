package com.capgemini.fooddelivery.service;

import com.capgemini.fooddelivery.entity.Delivery;
import com.capgemini.fooddelivery.entity.Order;
import com.capgemini.fooddelivery.repository.DeliveryRepository;
import com.capgemini.fooddelivery.repository.OrderRepository;
import com.capgemini.fooddelivery.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public String createDelivery(Long orderId, Delivery delivery) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return "ERROR: Order not found with id " + orderId;
        }
        if (paymentRepository.findByOrderId(orderId).isEmpty()) {
            return "ERROR: Payment must be completed before creating delivery";
        }
        if (deliveryRepository.findByOrderId(orderId).isPresent()) {
            return "ERROR: Delivery already exists for this order";
        }
        delivery.setOrder(order);
        deliveryRepository.save(delivery);
        return "Delivery created successfully";
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery getDeliveryByOrderId(Long orderId) {
        return deliveryRepository.findByOrderId(orderId).orElse(null);
    }

    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id).orElse(null);
    }
}
