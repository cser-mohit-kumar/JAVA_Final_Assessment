package com.capgemini.fooddelivery.service;

import com.capgemini.fooddelivery.entity.User;
import com.capgemini.fooddelivery.repository.UserRepository;
import com.capgemini.fooddelivery.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "ERROR: Email already registered";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return "ERROR: User not found";
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "ERROR: Invalid credentials";
        }

        return jwtUtil.generateToken(email);
    }
}
