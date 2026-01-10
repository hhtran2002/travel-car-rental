package com.rentalcar.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rentalcar.dto.BookingRequest;
import com.rentalcar.entity.Booking;
import com.rentalcar.repository.BookingRepository;

@Service
public class CustomerBookingService {

    private final BookingRepository bookingRepository;

    public CustomerBookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // ===================== 1. ĐẶT XE =====================
    // Tự lái (driverId = null) hoặc thuê tài xế (driverId != null)
    @Transactional
    public Booking createBooking(Long userId, BookingRequest request) {

        Booking booking = new Booking();

        // userId lấy từ JWT, KHÔNG lấy từ request
        booking.setUserId(userId);

        booking.setCarId(request.getCarId());
        booking.setDriverId(request.getDriverId()); // null nếu tự lái
        booking.setDiscountId(request.getDiscountId());

        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        booking.setPickupLocation(request.getPickupLocation());
        booking.setDropoffLocation(request.getDropoffLocation());
        booking.setNote(request.getNote());

        booking.setStatus("pending");
        booking.setTripStatus("assigned");
        booking.setCreatedAt(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    // ===================== 2. XEM DANH SÁCH ĐƠN THUÊ =====================
    public List<Booking> getMyBookings(Long userId) {
        return bookingRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // ===================== 3. XEM CHI TIẾT ĐƠN THUÊ =====================
    public Booking getBookingDetail(Long bookingId, Long userId) {

        Booking booking = bookingRepository
                .findByBookingIdAndUserId(bookingId, userId);

        if (booking == null) {
            throw new RuntimeException("Booking not found or access denied");
        }

        return booking;
    }

    // ===================== 4. HỦY ĐƠN THUÊ =====================
    @Transactional
    public Booking cancelBooking(Long bookingId, Long userId) {

        Booking booking = bookingRepository
                .findByBookingIdAndUserId(bookingId, userId);

        if (booking == null) {
            throw new RuntimeException("Booking not found or access denied");
        }

        if (!"pending".equals(booking.getStatus())) {
            throw new RuntimeException("Cannot cancel this booking");
        }

        booking.setStatus("cancelled");
        booking.setTripStatus("cancelled");

        return bookingRepository.save(booking);
    }

    // ===================== 5. NHẬN XE =====================
    @Transactional
    public Booking receiveCar(Long bookingId, Long userId) {

        Booking booking = bookingRepository
                .findByBookingIdAndUserId(bookingId, userId);

        if (booking == null) {
            throw new RuntimeException("Booking not found or access denied");
        }

        booking.setTripStatus("in_progress");

        return bookingRepository.save(booking);
    }

    // ===================== 6. TRẢ XE =====================
    @Transactional
    public Booking returnCar(Long bookingId, Long userId) {

        Booking booking = bookingRepository
                .findByBookingIdAndUserId(bookingId, userId);

        if (booking == null) {
            throw new RuntimeException("Booking not found or access denied");
        }

        booking.setTripStatus("completed");
        booking.setStatus("completed");

        return bookingRepository.save(booking);
    }
}
