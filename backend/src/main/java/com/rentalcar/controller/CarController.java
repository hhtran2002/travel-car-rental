package com.rentalcar.controller;

import com.rentalcar.entity.Car;
import com.rentalcar.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // GET /api/cars
    @GetMapping
    public List<Car> getAll() {
        return carService.getAllCars();
    }

    @GetMapping("/search")
    public List<Car> searchCars(
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long typeId,
            @RequestParam(required = false) String status
    ) {
        return carService.searchCars(modelName, brandId, typeId, status);
    }

}
