package com.rentalcar.controller.driver;

import com.rentalcar.entity.Booking;
import com.rentalcar.entity.Driver;
import com.rentalcar.service.BookingService;
import com.rentalcar.service.DriverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;
    private final BookingService bookingService;

    public DriverController(DriverService driverService, BookingService bookingService) {
        this.driverService = driverService;
        this.bookingService = bookingService;
    }

    // 1. Lấy thông tin Driver theo UserID (Dùng khi Login xong để load Profile)
    // GET: http://localhost:8080/api/drivers/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<Driver> getDriverByUserId(@PathVariable Long userId) {
        return driverService.getDriverByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 2. Cập nhật thông tin Driver
    // PUT: http://localhost:8080/api/drivers/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable Long id, @RequestBody Driver driverDetails) {
        try {
            Driver updatedDriver = driverService.updateDriver(id, driverDetails);
            return ResponseEntity.ok(updatedDriver);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. Xem lịch sử chuyến đi của tài xế
    // GET: http://localhost:8080/api/drivers/{id}/trips
    @GetMapping("/{id}/trips")
    public List<Booking> getDriverHistory(@PathVariable Long id) {
        return bookingService.getDriverHistory(id);
    }

    // 4. Cập nhật trạng thái chuyến đi (Nhận đơn / Hoàn thành / Hủy)
    // PATCH: http://localhost:8080/api/drivers/trips/{bookingId}/status?status=in_progress
    @PatchMapping("/trips/{bookingId}/status")
    public ResponseEntity<?> updateTripStatus(@PathVariable Long bookingId, @RequestParam String status) {
        try {
            // status hợp lệ: assigned, in_progress, completed, cancelled
            Booking updatedBooking = bookingService.updateTripStatus(bookingId, status);
            return ResponseEntity.ok(updatedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // NEW: Dashboard trips
    @GetMapping("/{id}/dashboard-trips")
    public ResponseEntity<?> getDashboardTrips(@PathVariable("id") Long driverId) {
        return ResponseEntity.ok(bookingService.getDriverDashboardTrips(driverId));
    }

    // NEW: Driver decision confirm/reject
    @PatchMapping("/{driverId}/bookings/{bookingId}/decision")
    public ResponseEntity<?> driverDecision(
            @PathVariable Long driverId,
            @PathVariable Long bookingId,
            @RequestParam String decision
    ) {
        try {
            Booking updated = bookingService.driverDecision(driverId, bookingId, decision);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}