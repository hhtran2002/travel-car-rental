package com.rentalcar.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rentalcar.entity.Booking;
import com.rentalcar.entity.Driver;
import com.rentalcar.repository.BookingRepository;
import com.rentalcar.repository.DriverRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final DriverRepository driverRepository;

    public BookingService(BookingRepository bookingRepository, DriverRepository driverRepository) {
        this.bookingRepository = bookingRepository;
        this.driverRepository = driverRepository;
    }

    // 1. Xem lịch sử chuyến đi của tài xế
    public List<Booking> getDriverHistory(Long driverId) {
        // Có thể dùng hàm findByDriverIdOrderByStartDateDesc nếu đã tạo trong Repository
        return bookingRepository.findByDriverId(driverId);
    }

    // 2. Cập nhật trạng thái chuyến đi (Xử lý logic nghiệp vụ phức tạp)
    @Transactional // Đảm bảo tính toàn vẹn dữ liệu (cập nhật cả 2 bảng hoặc không bảng nào)
    public Booking updateTripStatus(Long bookingId, String newStatus) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id " + bookingId));

        // Cập nhật trạng thái chuyến đi
        booking.setTripStatus(newStatus);
        
        // LOGIC TỰ ĐỘNG CẬP NHẬT TRẠNG THÁI TÀI XẾ
        // Nếu chuyến đi bắt đầu -> Tài xế bận
        if ("in_progress".equalsIgnoreCase(newStatus)) {
            updateDriverStatus(booking.getDriverId(), "busy");
        } 
        // Nếu chuyến đi kết thúc hoặc hủy -> Tài xế rảnh
        else if ("completed".equalsIgnoreCase(newStatus) || "cancelled".equalsIgnoreCase(newStatus)) {
            updateDriverStatus(booking.getDriverId(), "available");
        }

        return bookingRepository.save(booking);
    }

    // Hàm phụ trợ để cập nhật trạng thái tài xế
    private void updateDriverStatus(Long driverId, String status) {
        if (driverId != null) {
            Driver driver = driverRepository.findById(driverId).orElse(null);
            if (driver != null) {
                driver.setStatus(status);
                driverRepository.save(driver);
            }
        }
    }
}