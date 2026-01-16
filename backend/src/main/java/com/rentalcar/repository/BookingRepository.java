package com.rentalcar.repository;

import com.rentalcar.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /* ================= DRIVER ================= */

    // Tất cả booking của tài xế
    List<Booking> findByDriverId(Long driverId);

    // Booking theo tripStatus
    List<Booking> findByDriverIdAndTripStatus(Long driverId, String tripStatus);

    // Lịch sử chuyến đi (mới → cũ)
    List<Booking> findByDriverIdOrderByStartDateDesc(Long driverId);

    // Dashboard – đã confirmed
    List<Booking> findByDriverIdAndStatusOrderByCreatedAtDesc(Long driverId, String status);

    // Dashboard – đang chạy
    List<Booking> findByDriverIdAndTripStatusOrderByCreatedAtDesc(Long driverId, String tripStatus);

    // Dashboard – booking đang chờ tài xế nhận
    List<Booking> findByStatusAndDriverIdIsNullOrderByCreatedAtDesc(String status);


    /* ================= CUSTOMER ================= */

    // Danh sách booking của customer
    List<Booking> findByUserId(Long userId);

    // Danh sách booking (mới → cũ)
    List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Chi tiết booking của customer
    Booking findByBookingIdAndUserId(Long bookingId, Long userId);


    /* ================= ADMIN / SYSTEM ================= */

    /**
     * Kiểm tra trùng lịch xe
     * Logic trùng:
     * startA < endB AND endA > startB
     */
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
