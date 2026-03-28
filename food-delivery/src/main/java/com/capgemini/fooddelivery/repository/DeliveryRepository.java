package com.capgemini.fooddelivery.repository;
import com.capgemini.fooddelivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByOrderId(Long orderId);
}
