package com.rentalcar.controller;

import com.rentalcar.dto.BookingResponse;
import com.rentalcar.dto.CreateBookingRequest;
import com.rentalcar.entity.Booking;
import com.rentalcar.service.BookingService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // ================= HELPER: LẤY USER ID TỪ JWT =================
    private Long getCurrentUserId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("Chưa đăng nhập");
        }

        return (Long) authentication.getPrincipal();
    }

    // ================= CUSTOMER ĐẶT XE =================
    @PostMapping
    public BookingResponse createBooking(
            @RequestBody CreateBookingRequest req
    ) {
        Long userId = getCurrentUserId(); //  LẤY TỪ TOKEN
        req.setUserId(userId);

        Booking booking = bookingService.createBookingFromRequest(req);
        return BookingResponse.fromEntity(booking);
    }

    // ================= CUSTOMER XEM DANH SÁCH ĐƠN =================
    @GetMapping
    public List<BookingResponse> getMyBookings() {
        Long userId = getCurrentUserId();

        return bookingService.getCustomerBookings(userId)
                .stream()
                .map(BookingResponse::fromEntity)
                .toList();
    }

    // ================= CUSTOMER XEM CHI TIẾT 1 ĐƠN =================
    @GetMapping("/{bookingId}")
    public BookingResponse getMyBookingDetail(
            @PathVariable Long bookingId
    ) {
        Long userId = getCurrentUserId();

        return BookingResponse.fromEntity(
                bookingService.getCustomerBookingDetail(bookingId, userId)
        );
    }
}
