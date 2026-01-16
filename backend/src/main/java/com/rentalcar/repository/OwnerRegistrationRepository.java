package com.rentalcar.repository;

import com.rentalcar.entity.OwnerRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface OwnerRegistrationRepository extends JpaRepository<OwnerRegistration, Long> {

    Optional<OwnerRegistration> findByUserId(Long userId);

    List<OwnerRegistration> findByStatus(String status);
}
