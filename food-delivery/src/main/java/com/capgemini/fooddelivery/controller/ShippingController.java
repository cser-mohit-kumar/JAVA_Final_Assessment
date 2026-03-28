package com.capgemini.fooddelivery.controller;

import com.capgemini.fooddelivery.dto.ApiResponse;
import com.capgemini.fooddelivery.entity.Shipping;
import com.capgemini.fooddelivery.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @PostMapping
    public ResponseEntity<ApiResponse> createShipping(@RequestBody ShippingRequest request) {
        String result = shippingService.createShipping(request.getDeliveryId(), request.getShipping());
        if (result.startsWith("ERROR")) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("ERROR", result.replace("ERROR: ", "")));
        }
        return ResponseEntity.ok(new ApiResponse("SUCCESS", result));
    }

    @GetMapping
    public ResponseEntity<List<Shipping>> getAllShippings() {
        return ResponseEntity.ok(shippingService.getAllShippings());
    }

    @GetMapping("/delivery/{deliveryId}")
    public ResponseEntity<?> getShippingByDeliveryId(@PathVariable Long deliveryId) {
        Shipping shipping = shippingService.getShippingByDeliveryId(deliveryId);
        if (shipping == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("ERROR", "No shipping found for delivery " + deliveryId));
        }
        return ResponseEntity.ok(shipping);
    }

    // ── Inner request wrapper ──────────────────────────────────────────
    static class ShippingRequest {
        private Long deliveryId;
        private String address;
        private String estimatedTime;

        public Long getDeliveryId() { return deliveryId; }
        public void setDeliveryId(Long deliveryId) { this.deliveryId = deliveryId; }

        public Shipping getShipping() {
            Shipping s = new Shipping();
            s.setAddress(address);
            s.setEstimatedTime(estimatedTime);
            return s;
        }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public String getEstimatedTime() { return estimatedTime; }
        public void setEstimatedTime(String estimatedTime) { this.estimatedTime = estimatedTime; }
    }
}
