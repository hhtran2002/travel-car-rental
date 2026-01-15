//package com.rentalcar.service;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.rentalcar.entity.Booking;
//import com.rentalcar.entity.Driver;
//import com.rentalcar.repository.BookingRepository;
//import com.rentalcar.repository.DriverRepository;
//
//@Service
//public class BookingService {
//
//    private final BookingRepository bookingRepository;
//    private final DriverRepository driverRepository;
//
//    public BookingService(BookingRepository bookingRepository, DriverRepository driverRepository) {
//        this.bookingRepository = bookingRepository;
//        this.driverRepository = driverRepository;
//    }
//
//    // 1. Xem lịch sử chuyến đi của tài xế
//    public List<Booking> getDriverHistory(Long driverId) {
//        // Có thể dùng hàm findByDriverIdOrderByStartDateDesc nếu đã tạo trong Repository
//        return bookingRepository.findByDriverId(driverId);
//    }
//
//    // 2. Cập nhật trạng thái chuyến đi (Xử lý logic nghiệp vụ phức tạp)
//    @Transactional // Đảm bảo tính toàn vẹn dữ liệu (cập nhật cả 2 bảng hoặc không bảng nào)
//    public Booking updateTripStatus(Long bookingId, String newStatus) {
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new RuntimeException("Booking not found with id " + bookingId));
//
//        // Cập nhật trạng thái chuyến đi
//        booking.setTripStatus(newStatus);
//
//        // LOGIC TỰ ĐỘNG CẬP NHẬT TRẠNG THÁI TÀI XẾ
//        // Nếu chuyến đi bắt đầu -> Tài xế bận
//        if ("in_progress".equalsIgnoreCase(newStatus)) {
//            updateDriverStatus(booking.getDriverId(), "busy");
//        }
//        // Nếu chuyến đi kết thúc hoặc hủy -> Tài xế rảnh
//        else if ("completed".equalsIgnoreCase(newStatus) || "cancelled".equalsIgnoreCase(newStatus)) {
//            updateDriverStatus(booking.getDriverId(), "available");
//        }
//
//        return bookingRepository.save(booking);
//    }
//
//    // Hàm phụ trợ để cập nhật trạng thái tài xế
//    private void updateDriverStatus(Long driverId, String status) {
//        if (driverId != null) {
//            Driver driver = driverRepository.findById(driverId).orElse(null);
//            if (driver != null) {
//                driver.setStatus(status);
//                driverRepository.save(driver);
//            }
//        }
//    }
//}

package com.rentalcar.service;

import com.rentalcar.dto.CreateBookingRequest;
import com.rentalcar.entity.Booking;
import com.rentalcar.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // =====================================================
    // CUSTOMER
    // =====================================================

    // 1. Customer đặt xe
    public Booking createBookingFromRequest(CreateBookingRequest req) {

        // Validate thời gian
        if (req.getEndDate().isBefore(req.getStartDate())) {
            throw new RuntimeException("Ngày trả phải sau ngày nhận");
        }

        // Check trùng lịch xe
        List<Booking> conflicts = bookingRepository.findConflictBookings(
                req.getCarId(),
                req.getStartDate(),
                req.getEndDate()
        );

        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Xe đã được đặt trong khoảng thời gian này");
        }

        Booking booking = new Booking();
        booking.setUserId(req.getUserId());
        booking.setCarId(req.getCarId());
        booking.setDriverId(req.getDriverId());
        booking.setDiscountId(req.getDiscountId());
        booking.setStartDate(req.getStartDate());
        booking.setEndDate(req.getEndDate());
        booking.setPickupLocation(req.getPickupLocation());
        booking.setDropoffLocation(req.getDropoffLocation());
        booking.setNote(req.getNote());

        booking.setStatus("pending");
        booking.setTripStatus("assigned");
        booking.setCreatedAt(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    // 2. Customer xem danh sách đơn
    public List<Booking> getCustomerBookings(Long userId) {
        return bookingRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // 3. Customer xem chi tiết đơn
    public Booking getCustomerBookingDetail(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findByBookingIdAndUserId(bookingId, userId);
        if (booking == null) {
            throw new RuntimeException("Không tìm thấy đơn đặt xe");
        }
        return booking;
    }

    // =====================================================
    // DRIVER
    // =====================================================

    public List<Booking> getDriverBookings(Long driverId) {
        return bookingRepository.findByDriverIdOrderByStartDateDesc(driverId);
    }

    public Booking driverAcceptBooking(Long bookingId, Long driverId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        booking.setDriverId(driverId);
        booking.setStatus("confirmed");
        booking.setTripStatus("in_progress");

        return bookingRepository.save(booking);
    }

    public Booking completeTrip(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        booking.setStatus("completed");
        booking.setTripStatus("completed");

        return bookingRepository.save(booking);
    }

    // =====================================================
    // ADMIN
    // =====================================================

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking assignDriver(Long bookingId, Long driverId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        booking.setDriverId(driverId);
        booking.setTripStatus("assigned");

        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        booking.setStatus("cancelled");
        booking.setTripStatus("cancelled");

        return bookingRepository.save(booking);
    }
}
