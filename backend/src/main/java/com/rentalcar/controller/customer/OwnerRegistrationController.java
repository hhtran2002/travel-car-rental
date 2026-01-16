package com.rentalcar.controller.customer;

import com.rentalcar.entity.OwnerRegistration;
import com.rentalcar.service.OwnerRegistrationService;
import com.rentalcar.utils.SecurityUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/customer/owner-registration")
@PreAuthorize("hasRole('CUSTOMER')")
public class OwnerRegistrationController {

    private final OwnerRegistrationService service;

    public OwnerRegistrationController(OwnerRegistrationService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public OwnerRegistration register(
            @RequestParam String fullName,
            @RequestParam String phone,
            @RequestParam String carBrand,
            @RequestParam String carModel,
            @RequestParam String licensePlate,
            @RequestParam(required = false) String note,
            @RequestPart("cccdFront") MultipartFile cccdFront,
            @RequestPart("cavet") MultipartFile cavet
    ) {
        Long userId = SecurityUtils.currentUserId();
        return service.registerMultipart(userId, fullName, phone, carBrand, carModel, licensePlate, note, cccdFront, cavet);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        Long userId = SecurityUtils.currentUserId();
        OwnerRegistration reg = service.getByUser(userId);
        if (reg == null) return ResponseEntity.status(404).body("Chưa có hồ sơ đăng ký đối tác");
        return ResponseEntity.ok(reg);
    }
}
