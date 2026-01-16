package com.rentalcar.controller.driver;
import com.rentalcar.entity.Booking;
import com.rentalcar.repository.BookingRepository;
import com.rentalcar.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/driver")
@PreAuthorize("hasRole('DRIVER')")
public class DriverTripController {

    private final BookingRepository bookingRepository;

    public DriverTripController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/trips")
    public List<Booking> getMyTrips() {
        Long driverId = SecurityUtils.currentUserId();

        return bookingRepository.findByDriverIdAndStatusIn(
            driverId,
            List.of("ASSIGNED", "CONFIRMED", "IN_PROGRESS")
        );
    }

    @GetMapping("/trips/history")
    public List<Booking> getHistory() {
        Long driverId = SecurityUtils.currentUserId();

        return bookingRepository.findByDriverIdAndStatusIn(
            driverId,
            List.of("COMPLETED", "REJECTED")
        );
    }

    @PatchMapping("/trips/{id}/status")
    public void updateStatus(
        @PathVariable Long id,
        @RequestBody Map<String, String> body
    ) {
        String status = body.get("status");

        Booking b = bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Trip not found"));

        b.setStatus(status);
        bookingRepository.save(b);
    }
}
