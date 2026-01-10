package com.rentalcar.dto.admin;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;


import java.time.LocalDate;

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

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getStatus() {
        return status;
    }

    // ================= SETTERS =================
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
