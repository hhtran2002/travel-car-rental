package com.rentalcar.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contract")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
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

    @Column(name = "details", columnDefinition = "text")
    private String details;

    // pending, confirmed, active, completed, paid, cancelled
    @Column(name = "status", nullable = false)
    private String status = "pending";

    @Column(name = "signed_by", nullable = false)
    private String signedBy;
     // Tên Admin hoặc người đại diện ký
    @Column(name = "document_url")
    private String documentUrl; 
}