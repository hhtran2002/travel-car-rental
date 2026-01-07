package com.rentalcar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentalcar.entity.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    // Hàm này rất quan trọng: Giúp tìm thông tin tài xế dựa trên ID tài khoản đăng nhập
    // SELECT * FROM driver WHERE user_id = ?
    Optional<Driver> findByUserId(Long userId);
    
    // Tìm tài xế theo số bằng lái (để kiểm tra trùng lặp khi update)
    boolean existsByLicenseNum(String licenseNum);
}