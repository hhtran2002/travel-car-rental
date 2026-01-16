package com.rentalcar.controller.admin;

import com.rentalcar.entity.Driver;
import com.rentalcar.repository.DriverRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/drivers")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDriverController {

    private final DriverRepository driverRepository;

    public AdminDriverController(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }
}
