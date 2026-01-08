package com.rentalcar.repository;

import com.rentalcar.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    // Tìm hợp đồng theo mã booking để tránh tạo trùng
    Optional<Contract> findByBookingId(Long bookingId);
}