package com.capgemini.fooddelivery.controller;

import com.capgemini.fooddelivery.dto.ApiResponse;
import com.capgemini.fooddelivery.entity.Payment;
import com.capgemini.fooddelivery.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse> makePayment(@RequestBody PaymentRequest request) {
        String result = paymentService.makePayment(request.getOrderId(), request.getPayment());
        if (result.startsWith("ERROR")) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("ERROR", result.replace("ERROR: ", "")));
        }
        return ResponseEntity.ok(new ApiResponse("SUCCESS", result));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getPaymentByOrderId(@PathVariable Long orderId) {
        Payment payment = paymentService.getPaymentByOrderId(orderId);
        if (payment == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("ERROR", "No payment found for order " + orderId));
        }
        return ResponseEntity.ok(payment);
    }

    // ── Inner request wrapper ──────────────────────────────────────────
    static class PaymentRequest {
        private Long orderId;
        private String mode;
        private String status;

        public Long getOrderId() { return orderId; }
        public void setOrderId(Long orderId) { this.orderId = orderId; }

        public Payment getPayment() {
            Payment p = new Payment();
            p.setMode(mode);
            p.setStatus(status);
            return p;
        }

        public String getMode() { return mode; }
        public void setMode(String mode) { this.mode = mode; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}
