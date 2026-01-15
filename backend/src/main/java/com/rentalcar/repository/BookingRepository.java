package com.rentalcar.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rentalcar.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    //Driver
    // 1. Tìm tất cả chuyến đi được phân công cho tài xế này (Lịch sử chuyến đi)
    // SELECT * FROM booking WHERE driver_id = ?
    List<Booking> findByDriverId(Long driverId);

    // 2. Tìm các chuyến đi theo trạng thái cụ thể của tài xế 
    // (Ví dụ: Lấy các chuyến đang chạy "in_progress" để hiển thị lên màn hình chính)
    // SELECT * FROM booking WHERE driver_id = ? AND trip_status = ?
    List<Booking> findByDriverIdAndTripStatus(Long driverId, String tripStatus);
    
    // 3. (Tuỳ chọn) Sắp xếp lịch sử chuyến đi mới nhất lên đầu
    List<Booking> findByDriverIdOrderByStartDateDesc(Long driverId);

    // Customer
    //1. Lấy tất cả đơn thuê của customer
    List<Booking> findByUserId(Long userId);

    //2. Sắp xếp từ mới đến cũ
    List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);

    //3. Lấy 1 đơn cụ thể
    Booking findByBookingIdAndUserId(Long bookingId, Long userId);

    //Check trùng lịch xe
    // Kiểm tra xem xe đã được đặt trong khoảng thời gian này chưa
    // Logic trùng lịch:
    // startA < endB AND endA > startB
    @Query("""
        SELECT b FROM Booking b
        WHERE b.carId = :carId
          AND b.status <> 'cancelled'
          AND (:start < b.endDate AND :end > b.startDate)
    """)
    List<Booking> findConflictBookings(
            @Param("carId") Long carId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}