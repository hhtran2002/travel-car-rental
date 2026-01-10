//Customer
package com.rentalcar.dto;

import java.time.LocalDateTime;

public class BookingRequest {

    private Long userId;
    private Long carId;
    private Long driverId;   // null = tự lái
    private Long discountId;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String pickupLocation;
    private String dropoffLocation;
    private String note;

    // getters & setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }

    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }

    public Long getDiscountId() { return discountId; }
    public void setDiscountId(Long discountId) { this.discountId = discountId; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

    public String getDropoffLocation() { return dropoffLocation; }
    public void setDropoffLocation(String dropoffLocation) { this.dropoffLocation = dropoffLocation; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
