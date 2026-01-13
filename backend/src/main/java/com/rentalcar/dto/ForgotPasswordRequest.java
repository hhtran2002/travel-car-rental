package com.rentalcar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ForgotPasswordRequest {

    @NotBlank(message = "Email không được trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    // ================= CONSTRUCTOR =================
    public ForgotPasswordRequest() {
    }

    // ================= GETTER =================
    public String getEmail() {
        return email;
    }

    // ================= SETTER =================
    public void setEmail(String email) {
        this.email = email;
    }
}

