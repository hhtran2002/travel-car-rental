package com.rentalcar.service;

import com.rentalcar.entity.OwnerRegistration;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface OwnerRegistrationService {

    OwnerRegistration register(OwnerRegistration req);

    OwnerRegistration approve(Long id, String adminNote);

    OwnerRegistration reject(Long id, String adminNote);

    OwnerRegistration getByUser(Long userId);

    List<OwnerRegistration> getPending();

    // be/service/OwnerRegistrationService.java
    OwnerRegistration registerMultipart(
        Long userId,
        String fullName, String phone,
        String carBrand, String carModel, String licensePlate,
        String note,
        MultipartFile cccdFront, MultipartFile cavet
    );

}
