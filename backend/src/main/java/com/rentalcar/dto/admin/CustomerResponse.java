package com.rentalcar.dto.admin;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private Long userId;
    private String fullName;
    private String phone;
    private LocalDate dob;
    private String gender;
    private String avatar;

    private String email;
    private String status; // active / locked
}
