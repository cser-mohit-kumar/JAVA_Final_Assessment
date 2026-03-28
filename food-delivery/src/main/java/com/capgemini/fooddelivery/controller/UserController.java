package com.capgemini.fooddelivery.controller;

import com.capgemini.fooddelivery.dto.ApiResponse;
import com.capgemini.fooddelivery.entity.User;
import com.capgemini.fooddelivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user) {
        String result = userService.createUser(user);
        if (result.startsWith("ERROR")) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("ERROR", result.replace("ERROR: ", "")));
        }
        return ResponseEntity.ok(new ApiResponse("SUCCESS", result));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("ERROR", "User not found with id " + id));
        }
        return ResponseEntity.ok(user);
    }
}
