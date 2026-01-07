package com.rentalcar.service;

import com.rentalcar.dto.LoginRequest;
import com.rentalcar.dto.LoginResponse;
import com.rentalcar.dto.RegisterRequest;
import com.rentalcar.dto.RegisterResponse;

import com.rentalcar.dto.ForgotPasswordRequest;
import com.rentalcar.dto.ForgotPasswordResponse;
import com.rentalcar.dto.ResetPasswordRequest;

public interface AuthService {
    LoginResponse login(LoginRequest req);
    RegisterResponse registerCustomer(RegisterRequest req);

    ForgotPasswordResponse forgotPassword(ForgotPasswordRequest req);
    String resetPassword(ResetPasswordRequest req);

}
