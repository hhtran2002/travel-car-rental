package com.rentalcar.service;

import com.rentalcar.dto.CarDetailDTO;
import com.rentalcar.entity.Car;
import com.rentalcar.entity.CarImage;
import com.rentalcar.repository.CarImageRepository;
import com.rentalcar.repository.CarRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarImageRepository carImageRepository;

    public CarService(CarRepository carRepository,
                      CarImageRepository carImageRepository) {
        this.carRepository = carRepository;
        this.carImageRepository = carImageRepository;
    }

<<<<<<< HEAD
    // 1. Hiển thị danh sách tất cả xe
=======
    
>>>>>>> origin/minh
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

<<<<<<< HEAD
    // 2. Tìm kiếm xe
    public List<Car> searchCars(
            String modelName,
            Long brandId,
            Long typeId,
            String status
    ) {
        List<Car> cars;

=======
    public List<Car> searchCars(String modelName, Long brandId, Long typeId, String status) {
       
        List<Car> cars = carRepository.findAll();
>>>>>>> origin/minh
        if (status != null && !status.trim().isEmpty()) {
            cars = carRepository.findByStatus(status);
        }
<<<<<<< HEAD

        if (modelName != null && !modelName.trim().isEmpty()) {
            String keyword = modelName.trim().toLowerCase();
            cars = cars.stream()
                    .filter(car -> car.getModelName() != null
                            && car.getModelName().toLowerCase().contains(keyword))
                    .toList();
        }

        if (brandId != null) {
            cars = cars.stream()
                    .filter(car -> brandId.equals(car.getBrandId()))
                    .toList();
        }

        if (typeId != null) {
            cars = cars.stream()
                    .filter(car -> typeId.equals(car.getTypeId()))
                    .toList();
        }

        return cars;
    }

    // 3. Lấy chi tiết xe theo ID (DÙNG DTO)
    public CarDetailDTO getCarDetail(Long carId) {
        Car car = carRepository.findById(carId).orElse(null);
        if (car == null) return null;

        List<String> images = carImageRepository.findByCarId(carId)
                .stream()
                .map(CarImage::getLinkImage)
                .toList();

        CarDetailDTO dto = new CarDetailDTO();
        dto.setCarId(car.getCarId());
        dto.setModelName(car.getModelName());
        dto.setYear(car.getYear());
        dto.setStatus(car.getStatus());
        dto.setRating(car.getRating());
        dto.setMainImage(car.getMainImage());
        dto.setImages(images);

        return dto;
    }
}
=======
        if (modelName != null && !modelName.trim().isEmpty()) {
            String keyword = modelName.trim().toLowerCase();
            cars = cars.stream().filter(c -> c.getModelName().toLowerCase().contains(keyword)).toList();
        }
        if (brandId != null) cars = cars.stream().filter(c -> brandId.equals(c.getBrandId())).toList();
        if (typeId != null) cars = cars.stream().filter(c -> typeId.equals(c.getTypeId())).toList();
        return cars;
    }

    // --- Phần Admin (Của Nhân) 
    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    public Car updateCar(Long id, Car carDetails) {
        return carRepository.findById(id).map(car -> {
            car.setPlateNumber(carDetails.getPlateNumber());
            car.setBrandId(carDetails.getBrandId());
            car.setTypeId(carDetails.getTypeId());
            car.setModelName(carDetails.getModelName());
            car.setYear(carDetails.getYear());
            car.setStatus(carDetails.getStatus());
            car.setRating(carDetails.getRating());
            car.setMainImage(carDetails.getMainImage());
            return carRepository.save(car);
        }).orElseThrow(() -> new RuntimeException("Car not found with id " + id));
    }

    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) throw new RuntimeException("Car not found");
        carRepository.deleteById(id);
    }
}
>>>>>>> origin/minh
