package com.rentalcar.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.rentalcar.dto.PaymentRequest;
import com.rentalcar.entity.Payment;
import com.rentalcar.service.PaymentService;

@RestController
@RequestMapping("/api/customer/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(auth.getPrincipal().toString());
    }

    @PostMapping
    public ResponseEntity<Payment> pay(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(
                paymentService.pay(getCurrentUserId(), request)
        );
    }
}
