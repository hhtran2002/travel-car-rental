package com.rentalcar.repository;

import com.rentalcar.entity.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarImageRepository extends JpaRepository<CarImage, Long> {

    List<CarImage> findByCarId(Long carId);
}
