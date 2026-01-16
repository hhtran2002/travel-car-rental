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

    @GetMapping
    public List<Booking> listAll() {
        return bookingService.getAllBookings();
    }

    @PatchMapping("/{id}/confirm")
    public Booking confirm(@PathVariable Long id) {
        return bookingService.confirmBooking(id);
    }
}