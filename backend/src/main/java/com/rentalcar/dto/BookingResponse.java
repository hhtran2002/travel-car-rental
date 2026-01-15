package com.rentalcar.dto;

import com.rentalcar.entity.Booking;

import java.time.LocalDateTime;

public class BookingResponse {

    public Long bookingId;
    public Long userId;
    public Long carId;
    public Long driverId;
    public Long discountId;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    public String pickupLocation;
    public String dropoffLocation;
    public Long totalPrice;
    public String status;
    public String tripStatus;
    public LocalDateTime createdAt;
    public String note;

    // ===== Mapper =====
    public static BookingResponse fromEntity(Booking b) {
        BookingResponse res = new BookingResponse();
        res.bookingId = b.getBookingId();
        res.userId = b.getUserId();
        res.carId = b.getCarId();
        res.driverId = b.getDriverId();
        res.discountId = b.getDiscountId();
        res.startDate = b.getStartDate();
        res.endDate = b.getEndDate();
        res.pickupLocation = b.getPickupLocation();
        res.dropoffLocation = b.getDropoffLocation();
        res.totalPrice = b.getTotalPrice();
        res.status = b.getStatus();
        res.tripStatus = b.getTripStatus();
        res.createdAt = b.getCreatedAt();
        res.note = b.getNote();
        return res;
    }
}
