package com.rentalcar.controller;

import com.rentalcar.dto.BookingResponse;
import com.rentalcar.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/driver/bookings")
public class DriverBookingController {

    private final BookingService bookingService;

    public DriverBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // ================= DRIVER XEM DANH SÁCH CHUYẾN =================
    // GET: /api/driver/bookings/{driverId}
    @GetMapping("/{driverId}")
    public List<BookingResponse> getDriverBookings(@PathVariable Long driverId) {
        return bookingService.getDriverBookings(driverId)
                .stream()
                .map(BookingResponse::fromEntity)
                .toList();
    }
}

//    // ================= DRIVER NHẬN CHUYẾN =================
//    // PUT: /api/driver/bookings/{bookingId}/accept?driverId=1
//    @PutMapping("/{bookingId}/accept")
//    public ResponseEntity<BookingResponse> acceptBooking(
//            @PathVariable Long bookingId,
//            @RequestParam Long driverId
//    ) {
//        return ResponseEntity.ok(
//                BookingResponse.fromEntity(
//                        bookingService.driverAcceptBooking(bookingId, driverId)
//                )
//        );
//    }

//    // ================= DRIVER HOÀN THÀNH CHUYẾN =================
//    // PUT: /api/driver/bookings/{bookingId}/complete
//    @PutMapping("/{bookingId}/complete")
//    public ResponseEntity<BookingResponse> completeTrip(
//            @PathVariable Long bookingId
//    ) {
//        return ResponseEntity.ok(
//                BookingResponse.fromEntity(
//                        bookingService.completeTrip(bookingId)
//                )
//        );
//    }
//}
