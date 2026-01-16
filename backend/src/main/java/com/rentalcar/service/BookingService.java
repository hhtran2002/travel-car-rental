package com.rentalcar.service;

<<<<<<< HEAD
import com.rentalcar.dto.CreateBookingRequest;
=======
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

>>>>>>> origin/minh
import com.rentalcar.entity.Booking;
import com.rentalcar.entity.Driver;
import com.rentalcar.repository.BookingRepository;
import com.rentalcar.repository.DriverRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookingService {

<<<<<<< HEAD
    /* ================== CONSTANTS ================== */
=======
>>>>>>> origin/minh
    private static final String STATUS_PENDING = "pending";
    private static final String STATUS_CONFIRMED = "confirmed";
    private static final String STATUS_CANCELLED = "cancelled";
    private static final String STATUS_COMPLETED = "completed";

    private static final String TRIP_ASSIGNED = "assigned";
    private static final String TRIP_IN_PROGRESS = "in_progress";
    private static final String TRIP_COMPLETED = "completed";
    private static final String TRIP_CANCELLED = "cancelled";

<<<<<<< HEAD
    /* ================== DEPENDENCIES ================== */
=======
>>>>>>> origin/minh
    private final BookingRepository bookingRepository;
    private final DriverRepository driverRepository;
    private final ContractService contractService;

    public BookingService(
            BookingRepository bookingRepository,
            DriverRepository driverRepository,
            ContractService contractService
    ) {
        this.bookingRepository = bookingRepository;
        this.driverRepository = driverRepository;
        this.contractService = contractService;
    }

<<<<<<< HEAD
    /* ================== CUSTOMER ================== */

    public Booking createBookingFromRequest(CreateBookingRequest req) {
        Booking booking = new Booking();
        booking.setUserId(req.getUserId());
        booking.setCarId(req.getCarId());
        booking.setDiscountId(req.getDiscountId());
        booking.setStartDate(req.getStartDate());
        booking.setEndDate(req.getEndDate());
        booking.setPickupLocation(req.getPickupLocation());
        booking.setDropoffLocation(req.getDropoffLocation());
        booking.setNote(req.getNote());

        booking.setStatus(STATUS_PENDING);
        booking.setTripStatus(TRIP_ASSIGNED);
        booking.setCreatedAt(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    public List<Booking> getCustomerBookings(Long userId) {
        return bookingRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Booking getCustomerBookingDetail(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findByBookingIdAndUserId(bookingId, userId);
        if (booking == null) {
            throw new RuntimeException("Không tìm thấy booking của user");
        }
        return booking;
    }

    /* ================== ADMIN ================== */

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @Transactional
    public Booking confirmBooking(Long bookingId) {
        Booking booking = getBookingOrThrow(bookingId);

        if (!STATUS_PENDING.equals(booking.getStatus())) {
            throw new RuntimeException("Booking không ở trạng thái pending");
        }

        booking.setStatus(STATUS_CONFIRMED);
        bookingRepository.save(booking);

        contractService.createContractForBooking(booking);
        return booking;
    }

    @Transactional
    public Booking assignDriver(Long bookingId, Long driverId) {
        Booking booking = getBookingOrThrow(bookingId);
        Driver driver = getDriverOrThrow(driverId);

        booking.setDriverId(driver.getDriverId());
        booking.setTripStatus(TRIP_ASSIGNED);

        updateDriverStatus(driverId, "busy");
        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long bookingId) {
        Booking booking = getBookingOrThrow(bookingId);

        booking.setStatus(STATUS_CANCELLED);
        booking.setTripStatus(TRIP_CANCELLED);

        releaseDriverIfAny(booking);
        return bookingRepository.save(booking);
    }

    public Booking returnCar(Long bookingId) {
        Booking booking = getBookingOrThrow(bookingId);

        if (!TRIP_IN_PROGRESS.equals(booking.getTripStatus())) {
            throw new RuntimeException("Chưa nhận xe thì không thể trả");
        }

        booking.setTripStatus(TRIP_COMPLETED);
        booking.setStatus(STATUS_COMPLETED);
        releaseDriverIfAny(booking);

        return bookingRepository.save(booking);
    }

    /* ================== DRIVER ================== */

    public List<Booking> getDriverBookings(Long driverId) {
        return bookingRepository.findByDriverId(driverId);
    }

    public List<Booking> getDriverHistory(Long driverId) {
        return bookingRepository.findByDriverId(driverId);
    }

    public Map<String, List<Booking>> getDriverDashboardTrips(Long driverId) {
        Map<String, List<Booking>> res = new HashMap<>();

        res.put("requests",
                bookingRepository.findByStatusAndDriverIdIsNullOrderByCreatedAtDesc(STATUS_PENDING));

        res.put("confirmed",
                bookingRepository.findByDriverIdAndStatusOrderByCreatedAtDesc(driverId, STATUS_CONFIRMED));

        res.put("inProgress",
                bookingRepository.findByDriverIdAndTripStatusOrderByCreatedAtDesc(driverId, TRIP_IN_PROGRESS));

        return res;
    }

    @Transactional
    public Booking driverDecision(Long driverId, Long bookingId, String decision) {
        Booking booking = getBookingOrThrow(bookingId);

        if (!STATUS_PENDING.equalsIgnoreCase(booking.getStatus())) {
            throw new RuntimeException("Booking không còn PENDING");
        }

        if ("confirm".equalsIgnoreCase(decision)) {
            booking.setDriverId(driverId);
            booking.setStatus(STATUS_CONFIRMED);
            booking.setTripStatus(TRIP_ASSIGNED);
            updateDriverStatus(driverId, "busy");
            return bookingRepository.save(booking);
        }

        if ("reject".equalsIgnoreCase(decision)) {
            booking.setDriverId(null);
            booking.setStatus(STATUS_PENDING);
            booking.setTripStatus(TRIP_ASSIGNED);
            return bookingRepository.save(booking);
        }

        throw new RuntimeException("Decision không hợp lệ (confirm|reject)");
    }

    @Transactional
    public Booking updateTripStatus(Long bookingId, String newStatus) {
        Booking booking = getBookingOrThrow(bookingId);

        switch (newStatus.toLowerCase()) {
            case TRIP_IN_PROGRESS -> {
                requireTrip(booking, TRIP_ASSIGNED);
                requireStatus(booking, STATUS_CONFIRMED);
                booking.setTripStatus(TRIP_IN_PROGRESS);
            }
            case TRIP_COMPLETED -> {
                requireTrip(booking, TRIP_IN_PROGRESS);
                booking.setTripStatus(TRIP_COMPLETED);
                booking.setStatus(STATUS_COMPLETED);
                releaseDriverIfAny(booking);
            }
            case TRIP_CANCELLED -> {
                booking.setTripStatus(TRIP_CANCELLED);
                booking.setStatus(STATUS_CANCELLED);
                releaseDriverIfAny(booking);
            }
            default -> throw new RuntimeException("Trip status không hợp lệ");
        }

        return bookingRepository.save(booking);
    }

    /* ================== HELPERS ================== */

    private Booking getBookingOrThrow(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));
    }

    private Driver getDriverOrThrow(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế"));
    }

    private void updateDriverStatus(Long driverId, String status) {
        driverRepository.findById(driverId).ifPresent(d -> {
            d.setStatus(status);
            driverRepository.save(d);
        });
    }

    private void releaseDriverIfAny(Booking booking) {
        if (booking.getDriverId() != null) {
            updateDriverStatus(booking.getDriverId(), "available");
        }
    }

    private void requireTrip(Booking booking, String expected) {
        if (!expected.equalsIgnoreCase(booking.getTripStatus())) {
            throw new RuntimeException("TripStatus không hợp lệ");
=======
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
>>>>>>> origin/minh
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

<<<<<<< HEAD
    private void requireStatus(Booking booking, String expected) {
        if (!expected.equalsIgnoreCase(booking.getStatus())) {
            throw new RuntimeException("Booking status không hợp lệ");
        }
=======
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
>>>>>>> origin/minh
    }
}
