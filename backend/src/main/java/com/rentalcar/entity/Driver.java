package com.rentalcar.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "user_id", nullable = false)
    private Long userId; // Liên kết với bảng User

    @Column(name = "address")
    private String address;

    @Column(name = "exp_years")
    private Integer expYears;

    @Column(name = "rating", length = 20)
    private String rating;

    // DB enum: 'available','busy','inactive'
    // Lưu dưới dạng String để đơn giản hóa, giống như bạn đã làm ở Car.java
    @Column(name = "status", nullable = false, columnDefinition = "enum('available','busy','inactive')")
    private String status = "available";

    @Column(name = "license_num", nullable = false, length = 20, unique = true)
    private String licenseNum;

    // DB enum: 'B','D1','D2','D'
    @Column(name = "license_type", nullable = false, columnDefinition = "enum('B','D1','D2','D')")
    private String licenseType;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    // ================= GETTERS & SETTERS =================

    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getExpYears() { return expYears; }
    public void setExpYears(Integer expYears) { this.expYears = expYears; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getLicenseNum() { return licenseNum; }
    public void setLicenseNum(String licenseNum) { this.licenseNum = licenseNum; }

    public String getLicenseType() { return licenseType; }
    public void setLicenseType(String licenseType) { this.licenseType = licenseType; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
}