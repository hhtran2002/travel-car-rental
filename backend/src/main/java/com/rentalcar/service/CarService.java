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

    
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public List<Car> searchCars(String modelName, Long brandId, Long typeId, String status) {
       
        List<Car> cars = carRepository.findAll();
        if (status != null && !status.trim().isEmpty()) {
            cars = carRepository.findByStatus(status);
        }
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