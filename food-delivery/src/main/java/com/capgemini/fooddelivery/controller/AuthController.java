package com.capgemini.fooddelivery.controller;

import com.capgemini.fooddelivery.dto.ApiResponse;
import com.capgemini.fooddelivery.dto.LoginRequest;
import com.capgemini.fooddelivery.entity.User;
import com.capgemini.fooddelivery.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user) {
        String result = authService.register(user);
        if (result.startsWith("ERROR")) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("ERROR", result.replace("ERROR: ", "")));
        }
        return ResponseEntity.ok(new ApiResponse("SUCCESS", result));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        String result = authService.login(request.getEmail(), request.getPassword());
        if (result.startsWith("ERROR")) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse("ERROR", result.replace("ERROR: ", "")));
        }
        return ResponseEntity.ok(new ApiResponse("SUCCESS", result));
    }
}
