
package com.rentalcar.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rentalcar.entity.Driver;
import com.rentalcar.repository.DriverRepository;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    // 1. Lấy thông tin tài xế dựa trên User ID (Dùng khi load trang Profile)
    public Optional<Driver> getDriverByUserId(Long userId) {
        return driverRepository.findByUserId(userId);
    }

    // 2. Cập nhật thông tin tài xế
    public Driver updateDriver(Long driverId, Driver driverDetails) {
        return driverRepository.findById(driverId).map(driver -> {
            // Chỉ cho phép cập nhật các trường thông tin cá nhân cho phép
            driver.setAddress(driverDetails.getAddress());
            driver.setExpYears(driverDetails.getExpYears());
            driver.setLicenseNum(driverDetails.getLicenseNum());
            driver.setLicenseType(driverDetails.getLicenseType());
            driver.setExpiryDate(driverDetails.getExpiryDate());
            
            // Không cập nhật rating, status hay userId ở đây vì đó là logic hệ thống
            return driverRepository.save(driver);
        }).orElseThrow(() -> new RuntimeException("Driver not found with id " + driverId));
    }
}