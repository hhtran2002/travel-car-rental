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

    /* ================= DRIVER ================= */

    // Lịch sử chuyến đi của tài xế
    List<Booking> findByDriverId(Long driverId);

    // Các chuyến theo tripStatus (assigned / in_progress / completed / cancelled)
    List<Booking> findByDriverIdAndTripStatus(Long driverId, String tripStatus);

    // Lịch sử chuyến đi – sắp xếp mới nhất
    List<Booking> findByDriverIdOrderByStartDateDesc(Long driverId);

    // Dashboard – các chuyến đã confirmed
    List<Booking> findByDriverIdAndStatusOrderByCreatedAtDesc(Long driverId, String status);

    // Dashboard – các chuyến đang chạy
    List<Booking> findByDriverIdAndTripStatusOrderByCreatedAtDesc(Long driverId, String tripStatus);

    // Dashboard – các booking đang chờ tài xế nhận
    List<Booking> findByStatusAndDriverIdIsNullOrderByCreatedAtDesc(String status);


    /* ================= CUSTOMER ================= */

    // Danh sách booking của customer
    List<Booking> findByUserId(Long userId);

    // Danh sách booking (mới → cũ)
    List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Chi tiết booking của customer
    Booking findByBookingIdAndUserId(Long bookingId, Long userId);


    /* ================= ADMIN / SYSTEM ================= */

    // Kiểm tra trùng lịch xe
    // Logic trùng:
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
