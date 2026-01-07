package com.rentalcar.service.impl;

import com.rentalcar.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendResetOtp(String toEmail, String otp, int expiresMinutes) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toEmail);
        msg.setSubject("[RentalCar] Mã OTP đặt lại mật khẩu");
        msg.setText(
                "Bạn vừa yêu cầu đặt lại mật khẩu.\n" +
                        "Mã OTP của bạn là: " + otp + "\n" +
                        "OTP hết hạn sau " + expiresMinutes + " phút.\n\n" +
                        "Nếu không phải bạn, hãy bỏ qua email này."
        );
        mailSender.send(msg);
    }
}
