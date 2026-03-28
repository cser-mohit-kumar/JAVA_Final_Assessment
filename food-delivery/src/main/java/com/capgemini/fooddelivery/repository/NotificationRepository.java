package com.capgemini.fooddelivery.repository;
import com.capgemini.fooddelivery.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByOrderId(Long orderId);
}
