package com.rentalcar.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.rentalcar.dto.BookingRequest;
import com.rentalcar.entity.Booking;
import com.rentalcar.service.CustomerBookingService;

@RestController
@RequestMapping("/api/customer/bookings")
public class CustomerBookingController {

    private final CustomerBookingService bookingService;

    public CustomerBookingController(CustomerBookingService bookingService) {
        this.bookingService = bookingService;
    }

    // ===== helper: lấy userId từ JWT =====
    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Long) auth.getPrincipal();
    }

    // ===================== 1. ĐẶT XE =====================
    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestBody BookingRequest request
    ) {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(
                bookingService.createBooking(userId, request)
        );
    }

    // ===================== 2. XEM DANH SÁCH ĐƠN THUÊ =====================
    @GetMapping
    public ResponseEntity<List<Booking>> getMyBookings() {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(
                bookingService.getMyBookings(userId)
        );
    }

    // ===================== 3. XEM CHI TIẾT ĐƠN THUÊ =====================
    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingDetail(
            @PathVariable Long bookingId
    ) {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(
                bookingService.getBookingDetail(bookingId, userId)
        );
    }

    // ===================== 4. HỦY ĐƠN =====================
    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<Booking> cancelBooking(
            @PathVariable Long bookingId
    ) {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(
                bookingService.cancelBooking(bookingId, userId)
        );
    }

    // ===================== 5. NHẬN XE =====================
    @PutMapping("/{bookingId}/receive")
    public ResponseEntity<Booking> receiveCar(
            @PathVariable Long bookingId
    ) {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(
                bookingService.receiveCar(bookingId, userId)
        );
    }

    // ===================== 6. TRẢ XE =====================
    @PutMapping("/{bookingId}/return")
    public ResponseEntity<Booking> returnCar(
            @PathVariable Long bookingId
    ) {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(
                bookingService.returnCar(bookingId, userId)
        );
    }
}
