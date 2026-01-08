package com.rentalcar.service;

import com.rentalcar.entity.Car;
import com.rentalcar.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    //1. Hiển thị danh sách tất cả xe
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    //2. Tìm kiếm xe
    public List<Car> searchCars(
            String modelName,
            Long brandId,
            Long typeId,
            String status
    ) {
        List<Car> cars = carRepository.findAll();

        if (status != null && !status.trim().isEmpty()) {
            cars = carRepository.findByStatus(status);
        } else {
            cars = carRepository.findAll();
        }

        //Lọc theo tên
        if(modelName != null && !modelName.trim().isEmpty()) //" ","", null bỏ qua
        {
            String keyword = modelName.trim().toLowerCase(); //Xóa khoảng trắng, chuyển về chữ thường
            cars = cars.stream()
                    .filter(car -> car.getModelName() != null && car.getModelName().toLowerCase().contains(keyword))
                    .toList();

        }

        // Lọc theo hãng
        if (brandId != null) {
            cars = cars.stream()
                    .filter(car -> brandId.equals(car.getBrandId()))
                    .toList();
        }

        // Lọc theo loại
        if (typeId != null) {
            cars = cars.stream()
                    .filter(car -> typeId.equals(car.getTypeId()))
                    .toList();
        }
    return cars;
    }

<<<<<<< Updated upstream
=======
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
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Car not found with id " + id);
        }
        carRepository.deleteById(id);
    }
>>>>>>> Stashed changes
}
