package com.rentalcar.controller;

import com.rentalcar.dto.UserProfileResponse;
import com.rentalcar.entity.User;
import com.rentalcar.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/profile")
public class CustomerProfileController {

    private final UserRepository userRepo;

    public CustomerProfileController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(auth.getName()); // QUAN TRỌNG
    }

    @GetMapping
    public UserProfileResponse getProfile() {
        Long userId = getCurrentUserId();

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        UserProfileResponse res = new UserProfileResponse();
        res.setUserId(user.getUserId());
        res.setFullName(user.getFullName());
        res.setPhone(user.getPhone());
        res.setGender(user.getGender());
        res.setDob(user.getDob());
        res.setEmail(user.getAccount().getEmail());

        return res;
    }
}
