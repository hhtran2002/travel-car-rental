package com.rentalcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rentalcar.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByContractId(Long contractId);
}
