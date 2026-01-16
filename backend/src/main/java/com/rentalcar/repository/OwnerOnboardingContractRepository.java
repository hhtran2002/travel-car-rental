package com.rentalcar.repository;

import com.rentalcar.entity.OwnerOnboardingContract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OwnerOnboardingContractRepository extends JpaRepository<OwnerOnboardingContract, Long> {
    List<OwnerOnboardingContract> findByStatus(String status);
    Optional<OwnerOnboardingContract> findByUserId(Long userId);
}
