package com.rentalcar.controller;

import com.rentalcar.entity.CarImage;
import com.rentalcar.service.CarImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/car-images")
public class CarImageController {

    private final CarImageService carImageService;

    public CarImageController(CarImageService carImageService) {
        this.carImageService = carImageService;
    }

    @PostMapping("/{carId}")
    public CarImage uploadImage(
            @PathVariable Long carId,
            @RequestParam("image") MultipartFile image
    ) {
        return carImageService.uploadCarImage(carId, image);
    }
}
