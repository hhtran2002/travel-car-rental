package com.rentalcar.controller;

import com.rentalcar.dto.*;
import com.rentalcar.entity.Account;
import com.rentalcar.entity.User;
import com.rentalcar.repository.UserRepository;
import com.rentalcar.service.AuthService;
import com.rentalcar.security.*;
import com.rentalcar.utils.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthController(AuthService authService, UserRepository userRepository, JwtService jwtService) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
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

    // ✅ POST /api/auth/refresh
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh() {
        Long userId = SecurityUtils.currentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account acc = user.getAccount();
        if (acc == null || acc.getRole() == null) {
            throw new RuntimeException("Account/Role not found");
        }

        String roleName = acc.getRole().getRoleName();  // ví dụ: owner
        String token = jwtService.generateToken(userId, roleName);

        // LoginResponse của bạn đang có ctor như nào thì map tương ứng
        LoginResponse res = new LoginResponse(token, userId, acc.getEmail(), roleName);
        return ResponseEntity.ok(res);
    }
}
