package com.rentalcar.repository;

import com.rentalcar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhone(String phone);

    // ✅ check trùng phone nhưng loại trừ chính user đang sửa
    boolean existsByPhoneAndUserIdNot(String phone, Long userId);
}
