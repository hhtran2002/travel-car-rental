package com.rentalcar.controller.admin;

import com.rentalcar.entity.Booking;
import com.rentalcar.service.BookingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/bookings")
@PreAuthorize("hasRole('ADMIN')")
public class AdminBookingController {

    private final BookingService bookingService;

    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // ADMIN chỉ xem & quản lý booking
    @GetMapping
    public List<Booking> listAll() {
        return bookingService.getAllBookings();
    }
}
