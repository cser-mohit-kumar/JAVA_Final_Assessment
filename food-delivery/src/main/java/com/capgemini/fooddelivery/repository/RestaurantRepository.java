package com.capgemini.fooddelivery.repository;
import com.capgemini.fooddelivery.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {}
