package com.capgemini.fooddelivery.service;

import com.capgemini.fooddelivery.entity.Order;
import com.capgemini.fooddelivery.entity.Payment;
import com.capgemini.fooddelivery.repository.OrderRepository;
import com.capgemini.fooddelivery.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    public String makePayment(Long orderId, Payment payment) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return "ERROR: Order not found with id " + orderId;
        }
        if (paymentRepository.findByOrderId(orderId).isPresent()) {
            return "ERROR: Payment already exists for this order";
        }
        payment.setOrder(order);
        paymentRepository.save(payment);
        return "Payment recorded successfully";
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId).orElse(null);
    }
}
