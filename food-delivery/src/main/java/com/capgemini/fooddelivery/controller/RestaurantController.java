package com.capgemini.fooddelivery.controller;

import com.capgemini.fooddelivery.dto.ApiResponse;
import com.capgemini.fooddelivery.entity.Restaurant;
import com.capgemini.fooddelivery.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<ApiResponse> createRestaurant(@RequestBody Restaurant restaurant) {
        String result = restaurantService.createRestaurant(restaurant);
        return ResponseEntity.ok(new ApiResponse("SUCCESS", result));
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        if (restaurant == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("ERROR", "Restaurant not found with id " + id));
        }
        return ResponseEntity.ok(restaurant);
    }
}
