package com.rentalcar.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerateBCrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String adminPass = "Admin@123456";
        String driverPass = "Driver@123456";

        System.out.println("ADMIN_HASH = " + encoder.encode(adminPass));
        System.out.println("DRIVER_HASH = " + encoder.encode(driverPass));
    }
}
