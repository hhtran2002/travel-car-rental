package com.rentalcar.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(columnDefinition = "text")
    private String details;

    @Column(nullable = false)
    private String status;
    // pending, confirmed, active, completed, paid, cancelled

    @Column(name = "signed_by", nullable = false)
    private String signedBy;

    public Contract() {}

    public Long getContractId() { return contractId; }
    public Long getBookingId() { return bookingId; }
    public LocalDateTime getReturnDate() { return returnDate; }
    public Long getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }

    public void setContractId(Long contractId) { this.contractId = contractId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public void setReturnDate(LocalDateTime returnDate) { this.returnDate = returnDate; }
    public void setTotalPrice(Long totalPrice) { this.totalPrice = totalPrice; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public void setDetails(String details) { this.details = details; }
    public void setStatus(String status) { this.status = status; }
    public void setSignedBy(String signedBy) { this.signedBy = signedBy; }
}
