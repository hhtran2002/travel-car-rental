package com.rentalcar.service;

import com.rentalcar.entity.User;
import com.rentalcar.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User getById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy user"));
    }
}
