package com.rentalcar.service;

import com.rentalcar.entity.CarImage;
import com.rentalcar.repository.CarImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CarImageService {

    private final CloudinaryService cloudinaryService;
    private final CarImageRepository carImageRepository;

    public CarImageService(CloudinaryService cloudinaryService,
                           CarImageRepository carImageRepository) {
        this.cloudinaryService = cloudinaryService;
        this.carImageRepository = carImageRepository;
    }

    public CarImage uploadCarImage(Long carId, MultipartFile file) {

        String imageUrl = cloudinaryService.uploadImage(file);

        CarImage image = new CarImage();
        image.setCarId(carId);
        image.setLinkImage(imageUrl);

        return carImageRepository.save(image);
    }
}
