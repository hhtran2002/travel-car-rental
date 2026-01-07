package com.rentalcar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentalcar.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // 1. Tìm tất cả chuyến đi được phân công cho tài xế này (Lịch sử chuyến đi)
    // SELECT * FROM booking WHERE driver_id = ?
    List<Booking> findByDriverId(Long driverId);

    // 2. Tìm các chuyến đi theo trạng thái cụ thể của tài xế 
    // (Ví dụ: Lấy các chuyến đang chạy "in_progress" để hiển thị lên màn hình chính)
    // SELECT * FROM booking WHERE driver_id = ? AND trip_status = ?
    List<Booking> findByDriverIdAndTripStatus(Long driverId, String tripStatus);
    
    // 3. (Tuỳ chọn) Sắp xếp lịch sử chuyến đi mới nhất lên đầu
    List<Booking> findByDriverIdOrderByStartDateDesc(Long driverId);
}