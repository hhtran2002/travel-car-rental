package com.rentalcar.dto;

public class ForgotPasswordResponse {

    private String message;
    private String resetToken;       // có thể null
    private int expiresInMinutes;

    // ================= CONSTRUCTOR =================
    public ForgotPasswordResponse() {
    }

    public ForgotPasswordResponse(String message, String resetToken, int expiresInMinutes) {
        this.message = message;
        this.resetToken = resetToken;
        this.expiresInMinutes = expiresInMinutes;
    }

    // ================= GETTERS =================
    public String getMessage() {
        return message;
    }

    public String getResetToken() {
        return resetToken;
    }

    public int getExpiresInMinutes() {
        return expiresInMinutes;
    }

    // ================= SETTERS =================
    public void setMessage(String message) {
        this.message = message;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public void setExpiresInMinutes(int expiresInMinutes) {
        this.expiresInMinutes = expiresInMinutes;
    }
}
