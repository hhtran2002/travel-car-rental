package com.rentalcar.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Họ tên không được trống")
    private String fullName;

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

    @Pattern(
            regexp = "(^$|0\\d{9,10})",
            message = "Số điện thoại phải bắt đầu bằng 0 và gồm 10-11 chữ số"
    )
    private String phone;

    // ================= CONSTRUCTOR =================
    public RegisterRequest() {
    }

    // ================= GETTERS =================
    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    // ================= SETTERS =================
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
