package com.rentalcar.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "discount")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Discount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;
    
    @Column(name="discount_code", unique=true) private String discountCode;
    @Column(name="discount_name") private String discountName;
    private String status; // active/expired
    private Integer discountValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}