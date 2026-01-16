package com.rentalcar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "Email không được trống")
    @Email(message = "Email không hợp lệ")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email không đúng định dạng"
    )
    private String email;

    @NotBlank(message = "Mật khẩu không được trống")
    @Size(min = 6, message = "Mật khẩu tối thiểu 6 ký tự")
    private String password;

    // ================= CONSTRUCTOR =================
    public LoginRequest() {
    }

    // ================= GETTERS =================
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // ================= SETTERS =================
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
