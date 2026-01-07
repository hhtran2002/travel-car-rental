package com.rentalcar.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Long userId;
    private String email;
    private String role; // admin/driver/customer
}
