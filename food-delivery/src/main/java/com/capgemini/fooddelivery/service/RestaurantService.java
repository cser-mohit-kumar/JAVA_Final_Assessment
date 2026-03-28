package com.capgemini.fooddelivery.service;

import com.capgemini.fooddelivery.entity.Restaurant;
import com.capgemini.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public String createRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
        return "Restaurant created successfully";
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }
}
