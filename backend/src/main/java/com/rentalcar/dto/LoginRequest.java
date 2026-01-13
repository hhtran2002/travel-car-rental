package com.rentalcar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Email không được trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Mật khẩu không được trống")
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
