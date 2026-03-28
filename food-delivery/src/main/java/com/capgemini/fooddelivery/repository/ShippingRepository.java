package com.capgemini.fooddelivery.repository;
import com.capgemini.fooddelivery.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ShippingRepository extends JpaRepository<Shipping, Long> {
    Optional<Shipping> findByDeliveryId(Long deliveryId);
}
