package com.rentalcar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequest {

    @NotBlank(message = "Email không được trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Token không được trống")
    private String token;

    @NotBlank(message = "Mật khẩu mới không được trống")
    @Size(min = 6, message = "Mật khẩu mới tối thiểu 6 ký tự")
    private String newPassword;

    // ================= CONSTRUCTOR =================
    public ResetPasswordRequest() {
    }

    // ================= GETTERS =================
    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    // ================= SETTERS =================
    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
