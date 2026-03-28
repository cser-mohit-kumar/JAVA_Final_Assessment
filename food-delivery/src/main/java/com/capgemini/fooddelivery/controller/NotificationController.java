package com.capgemini.fooddelivery.controller;

import com.capgemini.fooddelivery.dto.ApiResponse;
import com.capgemini.fooddelivery.entity.Notification;
import com.capgemini.fooddelivery.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ApiResponse> sendNotification(@RequestBody NotificationRequest request) {
        String result = notificationService.sendNotification(request.getOrderId(), request.getNotification());
        if (result.startsWith("ERROR")) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("ERROR", result.replace("ERROR: ", "")));
        }
        return ResponseEntity.ok(new ApiResponse("SUCCESS", result));
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getNotificationByOrderId(@PathVariable Long orderId) {
        Notification notification = notificationService.getNotificationByOrderId(orderId);
        if (notification == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("ERROR", "No notification found for order " + orderId));
        }
        return ResponseEntity.ok(notification);
    }

    // ── Inner request wrapper ──────────────────────────────────────────
    static class NotificationRequest {
        private Long orderId;
        private String message;

        public Long getOrderId() { return orderId; }
        public void setOrderId(Long orderId) { this.orderId = orderId; }

        public Notification getNotification() {
            Notification n = new Notification();
            n.setMessage(message);
            return n;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
