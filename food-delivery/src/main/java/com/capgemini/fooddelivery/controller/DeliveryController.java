package com.capgemini.fooddelivery.controller;

import com.capgemini.fooddelivery.dto.ApiResponse;
import com.capgemini.fooddelivery.entity.Delivery;
import com.capgemini.fooddelivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<ApiResponse> createDelivery(@RequestBody DeliveryRequest request) {
        String result = deliveryService.createDelivery(request.getOrderId(), request.getDelivery());
        if (result.startsWith("ERROR")) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("ERROR", result.replace("ERROR: ", "")));
        }
        return ResponseEntity.ok(new ApiResponse("SUCCESS", result));
    }

    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.getAllDeliveries());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getDeliveryByOrderId(@PathVariable Long orderId) {
        Delivery delivery = deliveryService.getDeliveryByOrderId(orderId);
        if (delivery == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("ERROR", "No delivery found for order " + orderId));
        }
        return ResponseEntity.ok(delivery);
    }

    // ── Inner request wrapper ──────────────────────────────────────────
    static class DeliveryRequest {
        private Long orderId;
        private String deliveryPartner;
        private String status;

        public Long getOrderId() { return orderId; }
        public void setOrderId(Long orderId) { this.orderId = orderId; }

        public Delivery getDelivery() {
            Delivery d = new Delivery();
            d.setDeliveryPartner(deliveryPartner);
            d.setStatus(status);
            return d;
        }

        public String getDeliveryPartner() { return deliveryPartner; }
        public void setDeliveryPartner(String deliveryPartner) { this.deliveryPartner = deliveryPartner; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}
