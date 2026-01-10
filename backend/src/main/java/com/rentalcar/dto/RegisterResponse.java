package com.rentalcar.dto;

public class RegisterResponse {

    private Long userId;
    private String email;
    private String role;

    // ================= CONSTRUCTORS =================
    public RegisterResponse() {
    }

    public RegisterResponse(Long userId, String email, String role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    // ================= GETTERS =================
    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    // ================= SETTERS =================
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
