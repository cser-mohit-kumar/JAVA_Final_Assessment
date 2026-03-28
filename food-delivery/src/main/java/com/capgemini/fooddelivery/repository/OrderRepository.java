package com.capgemini.fooddelivery.repository;
import com.capgemini.fooddelivery.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<Order, Long> {}
