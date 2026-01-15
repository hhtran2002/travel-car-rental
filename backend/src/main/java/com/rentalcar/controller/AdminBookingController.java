package com.rentalcar.controller;

import com.rentalcar.dto.AssignDriverRequest;
import com.rentalcar.dto.BookingResponse;
import com.rentalcar.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/bookings")
public class AdminBookingController {

    private final BookingService bookingService;

    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // ================= ADMIN XEM TẤT CẢ ĐƠN =================
    @GetMapping
    public List<BookingResponse> getAllBookings() {
        return bookingService.getAllBookings()
                .stream()
                .map(BookingResponse::fromEntity)
                .toList();
    }

    // ================= ADMIN PHÂN TÀI XẾ =================
    @PutMapping("/{bookingId}/assign-driver")
    public ResponseEntity<BookingResponse> assignDriver(
            @PathVariable Long bookingId,
            @RequestBody AssignDriverRequest req
    ) {
        return ResponseEntity.ok(
                BookingResponse.fromEntity(
                        bookingService.assignDriver(bookingId, req.driverId)
                )
        );
    }

    // ================= ADMIN HỦY ĐƠN =================
    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Long bookingId
    ) {
        return ResponseEntity.ok(
                BookingResponse.fromEntity(
                        bookingService.cancelBooking(bookingId)
                )
        );
    }
}
