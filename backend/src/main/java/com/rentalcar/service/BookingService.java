package com.rentalcar.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rentalcar.entity.Booking;
import com.rentalcar.entity.Driver;
import com.rentalcar.repository.BookingRepository;
import com.rentalcar.repository.DriverRepository;

@Service
public class BookingService {

    private static final String STATUS_PENDING = "pending";
    private static final String STATUS_CONFIRMED = "confirmed";
    private static final String STATUS_CANCELLED = "cancelled";
    private static final String STATUS_COMPLETED = "completed";

    private static final String TRIP_ASSIGNED = "assigned";
    private static final String TRIP_IN_PROGRESS = "in_progress";
    private static final String TRIP_COMPLETED = "completed";
    private static final String TRIP_CANCELLED = "cancelled";

    private final BookingRepository bookingRepository;
    private final DriverRepository driverRepository;

    public BookingService(BookingRepository bookingRepository, DriverRepository driverRepository) {
        this.bookingRepository = bookingRepository;
        this.driverRepository = driverRepository;
    }

    // 1) Lịch sử chuyến đi của tài xế
    public List<Booking> getDriverHistory(Long driverId) {
        return bookingRepository.findByDriverId(driverId);
    }

    // 2) Driver dashboard trips (requests/confirmed/inProgress)
    public Map<String, List<Booking>> getDriverDashboardTrips(Long driverId) {
        List<Booking> requests = bookingRepository
                .findByStatusAndDriverIdIsNullOrderByCreatedAtDesc(STATUS_PENDING);

        List<Booking> confirmed = bookingRepository
                .findByDriverIdAndStatusOrderByCreatedAtDesc(driverId, STATUS_CONFIRMED);

        List<Booking> inProgress = bookingRepository
                .findByDriverIdAndTripStatusOrderByCreatedAtDesc(driverId, TRIP_IN_PROGRESS);

        Map<String, List<Booking>> res = new HashMap<>();
        res.put("requests", requests);
        res.put("confirmed", confirmed);
        res.put("inProgress", inProgress);
        return res;
    }

    // 3) Driver decision: confirm/reject
    // decision: "confirm" | "reject"
    @Transactional
    public Booking driverDecision(Long driverId, Long bookingId, String decision) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // chỉ quyết định khi booking đang pending
        if (!STATUS_PENDING.equalsIgnoreCase(booking.getStatus())) {
            throw new RuntimeException("Booking không còn PENDING để quyết định");
        }

        // nếu đã có driver khác nhận -> chặn
        if (booking.getDriverId() != null && !booking.getDriverId().equals(driverId)) {
            throw new RuntimeException("Booking đã được tài xế khác nhận");
        }

        // CONFIRM
        if ("confirm".equalsIgnoreCase(decision)) {
            booking.setDriverId(driverId);
            booking.setStatus(STATUS_CONFIRMED);

            // đảm bảo tripStatus = assigned khi mới nhận
            if (booking.getTripStatus() == null) booking.setTripStatus(TRIP_ASSIGNED);

            return bookingRepository.save(booking);
        }

        // REJECT (MVP chuẩn): giữ booking để tài xế khác còn nhận
        if ("reject".equalsIgnoreCase(decision)) {
            booking.setDriverId(null);
            booking.setStatus(STATUS_PENDING);
            booking.setTripStatus(TRIP_ASSIGNED);
            return bookingRepository.save(booking);
        }

        throw new RuntimeException("Decision không hợp lệ. Dùng confirm|reject");
    }

    // wrapper (nếu chỗ khác đang gọi confirmBooking)
    public Booking confirmBooking(Long bookingId, Long driverId) {
        return driverDecision(driverId, bookingId, "confirm");
    }

    // 4) Update trip status: start/complete/cancel (guard state machine)
    @Transactional
    public Booking updateTripStatus(Long bookingId, String newStatus) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id " + bookingId));

        String currentTrip = booking.getTripStatus();

        if (!isTripStatusValid(newStatus)) {
            throw new RuntimeException("Trip status không hợp lệ");
        }

        // START: assigned -> in_progress (chỉ khi booking.status=confirmed và có driverId)
        if (TRIP_IN_PROGRESS.equalsIgnoreCase(newStatus)) {
            if (!TRIP_ASSIGNED.equalsIgnoreCase(currentTrip)) {
                throw new RuntimeException("Chỉ được START khi tripStatus=assigned");
            }
            if (!STATUS_CONFIRMED.equalsIgnoreCase(booking.getStatus())) {
                throw new RuntimeException("Chỉ được START khi booking.status=confirmed");
            }
            if (booking.getDriverId() == null) {
                throw new RuntimeException("Booking chưa gán tài xế");
            }

            booking.setTripStatus(TRIP_IN_PROGRESS);
            updateDriverStatus(booking.getDriverId(), "busy");
            return bookingRepository.save(booking);
        }

        // COMPLETE: in_progress -> completed (sync booking.status)
        if (TRIP_COMPLETED.equalsIgnoreCase(newStatus)) {
            if (!TRIP_IN_PROGRESS.equalsIgnoreCase(currentTrip)) {
                throw new RuntimeException("Chỉ được COMPLETE khi tripStatus=in_progress");
            }
            if (booking.getDriverId() == null) {
                throw new RuntimeException("Booking chưa gán tài xế");
            }

            booking.setTripStatus(TRIP_COMPLETED);
            booking.setStatus(STATUS_COMPLETED);
            updateDriverStatus(booking.getDriverId(), "available");
            return bookingRepository.save(booking);
        }

        // CANCEL: allowed from assigned/in_progress (sync booking.status)
        if (TRIP_CANCELLED.equalsIgnoreCase(newStatus)) {
            if (!(TRIP_ASSIGNED.equalsIgnoreCase(currentTrip) || TRIP_IN_PROGRESS.equalsIgnoreCase(currentTrip))) {
                throw new RuntimeException("Chỉ được CANCEL khi assigned hoặc in_progress");
            }
            if (booking.getDriverId() == null) {
                throw new RuntimeException("Booking chưa gán tài xế");
            }

            booking.setTripStatus(TRIP_CANCELLED);
            booking.setStatus(STATUS_CANCELLED);
            updateDriverStatus(booking.getDriverId(), "available");
            return bookingRepository.save(booking);
        }

        // không cho quay về assigned
        throw new RuntimeException("Không hỗ trợ chuyển về assigned");
    }

    private boolean isTripStatusValid(String s) {
        if (s == null) return false;
        String v = s.toLowerCase();
        return TRIP_ASSIGNED.equals(v) || TRIP_IN_PROGRESS.equals(v) || TRIP_COMPLETED.equals(v) || TRIP_CANCELLED.equals(v);
    }

    private void updateDriverStatus(Long driverId, String status) {
        if (driverId == null) return;

        Driver driver = driverRepository.findById(driverId).orElse(null);
        if (driver == null) return;

        driver.setStatus(status);
        driverRepository.save(driver);
    }

    // (tuỳ bạn dùng ở admin)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll(Sort.by("createdAt").descending());
    }
}
