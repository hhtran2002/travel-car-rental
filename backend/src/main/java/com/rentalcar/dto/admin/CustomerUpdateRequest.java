package com.rentalcar.dto.admin;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateRequest {
    private String fullName;
    private String phone;

    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    private LocalDate dob;
    private String gender;
    private String avatar;

    // optional: admin khoá/mở tài khoản
    // chỉ nhận active hoặc locked (null thì bỏ qua)
    @Pattern(regexp = "^(active|locked)$", message = "Status chỉ nhận 'active' hoặc 'locked'")
    private String status;
}
