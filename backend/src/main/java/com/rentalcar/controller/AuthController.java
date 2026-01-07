package com.rentalcar.controller;

import com.rentalcar.dto.LoginRequest;
import com.rentalcar.dto.LoginResponse;
import com.rentalcar.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rentalcar.dto.RegisterRequest;
import com.rentalcar.dto.RegisterResponse;
import com.rentalcar.dto.ForgotPasswordRequest;
import com.rentalcar.dto.ForgotPasswordResponse;
import com.rentalcar.dto.ResetPasswordRequest;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.registerCustomer(req));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest req) {
        return ResponseEntity.ok(authService.forgotPassword(req));
    }



    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest req) {
        return ResponseEntity.ok(java.util.Map.of("message", authService.resetPassword(req)));
    }


}
