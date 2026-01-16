package com.rentalcar.controller.admin;

import com.rentalcar.dto.AssignDriverRequest;
import com.rentalcar.dto.BookingResponse;
import com.rentalcar.service.BookingService;
import org.springframework.http.ResponseEntity;
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

    // ================= ADMIN XEM TẤT CẢ BOOKING =================
    @GetMapping
    public List<BookingResponse> getAllBookings() {
        return bookingService.getAllBookings()
                .stream()
                .map(BookingResponse::fromEntity)
                .toList();
    }

    // ================= ADMIN XÁC NHẬN BOOKING =================
    @PutMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingResponse> confirmBooking(
            @PathVariable Long bookingId
    ) {
        return ResponseEntity.ok(
                BookingResponse.fromEntity(
                        bookingService.confirmBooking(bookingId)
                )
        );
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

    // ================= ADMIN NHẬN XE (START TRIP) =================
    @PutMapping("/{bookingId}/pickup")
    public ResponseEntity<BookingResponse> pickupCar(
            @PathVariable Long bookingId
    ) {
        return ResponseEntity.ok(
                BookingResponse.fromEntity(
                        bookingService.updateTripStatus(bookingId, "in_progress")
                )
        );
    }

    // ================= ADMIN TRẢ XE =================
    @PutMapping("/{bookingId}/return")
    public ResponseEntity<BookingResponse> returnCar(
            @PathVariable Long bookingId
    ) {
        return ResponseEntity.ok(
                BookingResponse.fromEntity(
                        bookingService.returnCar(bookingId)
                )
        );
    }

    // ================= ADMIN HỦY BOOKING =================
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
