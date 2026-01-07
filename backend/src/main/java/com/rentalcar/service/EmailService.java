package com.rentalcar.service;

public interface EmailService {
    void sendResetOtp(String toEmail, String otp, int expiresMinutes);
}
