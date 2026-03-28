package com.capgemini.fooddelivery.service;

import com.capgemini.fooddelivery.entity.Notification;
import com.capgemini.fooddelivery.entity.Order;
import com.capgemini.fooddelivery.repository.NotificationRepository;
import com.capgemini.fooddelivery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private OrderRepository orderRepository;

    public String sendNotification(Long orderId, Notification notification) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return "ERROR: Order not found with id " + orderId;
        }
        if (notificationRepository.findByOrderId(orderId).isPresent()) {
            return "ERROR: Notification already sent for this order";
        }
        notification.setOrder(order);
        notificationRepository.save(notification);
        return "Notification sent successfully";
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationByOrderId(Long orderId) {
        return notificationRepository.findByOrderId(orderId).orElse(null);
    }
}
