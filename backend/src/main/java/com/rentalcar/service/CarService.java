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

    public CarService(
            CarRepository carRepository,
            CarImageRepository carImageRepository
    ) {
        this.carRepository = carRepository;
        this.carImageRepository = carImageRepository;
    }

    // ================= CUSTOMER =================

    // 1. Hiển thị danh sách tất cả xe
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // 2. Tìm kiếm xe
    public List<Car> searchCars(
            String modelName,
            Long brandId,
            Long typeId,
            String status
    ) {

        List<Car> cars = carRepository.findAll();

        if (status != null && !status.trim().isEmpty()) {
            cars = cars.stream()
                    .filter(c -> status.equalsIgnoreCase(c.getStatus()))
                    .toList();
        }

        if (modelName != null && !modelName.trim().isEmpty()) {
            String keyword = modelName.trim().toLowerCase();
            cars = cars.stream()
                    .filter(c -> c.getModelName() != null
                            && c.getModelName().toLowerCase().contains(keyword))
                    .toList();
        }

        if (brandId != null) {
            cars = cars.stream()
                    .filter(c -> brandId.equals(c.getBrandId()))
                    .toList();
        }

        if (typeId != null) {
            cars = cars.stream()
                    .filter(c -> typeId.equals(c.getTypeId()))
                    .toList();
        }

        return cars;
    }

    // 3. Lấy chi tiết xe theo ID (DÙNG DTO)
    public CarDetailDTO getCarDetail(Long carId) {

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy xe"));

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

    // ================= ADMIN =================

    // 4. Thêm xe
    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    // 5. Cập nhật xe
    public Car updateCar(Long id, Car carDetails) {

        return carRepository.findById(id)
                .map(car -> {
                    car.setPlateNumber(carDetails.getPlateNumber());
                    car.setBrandId(carDetails.getBrandId());
                    car.setTypeId(carDetails.getTypeId());
                    car.setModelName(carDetails.getModelName());
                    car.setYear(carDetails.getYear());
                    car.setStatus(carDetails.getStatus());
                    car.setRating(carDetails.getRating());
                    car.setMainImage(carDetails.getMainImage());
                    return carRepository.save(car);
                })
                .orElseThrow(() -> new RuntimeException("Không tìm thấy xe với id " + id));
    }

    // 6. Xóa xe
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy xe");
        }
        carRepository.deleteById(id);
    }
}
