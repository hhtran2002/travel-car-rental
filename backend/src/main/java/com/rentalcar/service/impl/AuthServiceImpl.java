package com.rentalcar.service.impl;

import com.rentalcar.dto.*;
import com.rentalcar.entity.*;
import com.rentalcar.exception.ConflictException;
import com.rentalcar.exception.ForbiddenException;
import com.rentalcar.repository.*;
import com.rentalcar.security.JwtService;
import com.rentalcar.service.AuthService;
import com.rentalcar.service.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import com.rentalcar.exception.UnauthorizedException;


import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepo;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordResetRepository passwordResetRepo;
    private final JwtService jwtService;
    private final EmailService emailService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthServiceImpl(AccountRepository accountRepo,
                           UserRepository userRepo,
                           RoleRepository roleRepo,
                           PasswordResetRepository passwordResetRepo,
                           JwtService jwtService,
                           EmailService emailService) {
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordResetRepo = passwordResetRepo;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    @Override
    public LoginResponse login(LoginRequest req) {
        Account acc = accountRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Sai email hoặc mật khẩu"));

        if (acc.getStatus() == Account.Status.locked) {
            throw new ForbiddenException("Tài khoản đã bị khóa");
        }

        String stored = acc.getPassword();
        boolean ok = stored != null && encoder.matches(req.getPassword(), stored);


        if (!ok) throw new UnauthorizedException("Sai email hoặc mật khẩu");


        String roleName = acc.getRole().getRoleName();
        String token = jwtService.generateToken(acc.getUserId(), roleName);

        return new LoginResponse(token, acc.getUserId(), acc.getEmail(), roleName);
    }


    @Override
    @Transactional
    public RegisterResponse registerCustomer(RegisterRequest req) {
        if (accountRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new ConflictException("Email đã tồn tại");
        }

        User user = new User();
        user.setFullName(req.getFullName());
        String phone = (req.getPhone() == null || req.getPhone().isBlank())
                ? null
                : req.getPhone().trim();

        // nếu bạn muốn check trùng phone (khuyến nghị)
        if (phone != null && userRepo.existsByPhone(phone)) {
            throw new ConflictException("Số điện thoại đã tồn tại");
        }

        user.setPhone(phone);
        user = userRepo.save(user);


        Role customerRole = roleRepo.findByRoleName("customer")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role customer"));


        Account acc = new Account();
        acc.setUser(user);
        acc.setEmail(req.getEmail());
        acc.setPassword(encoder.encode(req.getPassword()));
        acc.setStatus(Account.Status.active);
        acc.setCreatedAt(LocalDateTime.now());
        acc.setRole(customerRole);

        accountRepo.save(acc);

        return new RegisterResponse(user.getUserId(), acc.getEmail(), customerRole.getRoleName());
    }

    @Override
    @Transactional
    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest req) {

        String commonMsg = "Nếu email tồn tại, hệ thống đã gửi OTP đặt lại mật khẩu";
        int expiresMinutes = 10;

        Account acc = accountRepo.findByEmail(req.getEmail()).orElse(null);

        // chống dò email
        if (acc == null) {
            return new ForgotPasswordResponse(commonMsg, null, expiresMinutes);
        }

        // chỉ customer dùng
        if (!"customer".equalsIgnoreCase(acc.getRole().getRoleName())) {
            return new ForgotPasswordResponse(commonMsg, null, expiresMinutes);
        }

        // ✅ vô hiệu OTP cũ
        passwordResetRepo.invalidateAllActiveByUserId(acc.getUserId());

        // OTP 6 số
        String otp = String.format("%06d", new java.security.SecureRandom().nextInt(1_000_000));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime exp = now.plusMinutes(expiresMinutes);

        // ✅ hash OTP lưu DB
        String otpHash = encoder.encode(otp);

        PasswordReset pr = new PasswordReset();
        pr.setUserId(acc.getUserId());
        pr.setToken(otpHash);   // lưu hash
        pr.setExpiresAt(exp);
        pr.setUsed(false);
        pr.setCreatedAt(now);

        passwordResetRepo.save(pr);

        // gửi OTP qua email
        emailService.sendResetOtp(acc.getEmail(), otp, expiresMinutes);

        // ✅ Trả message thôi (không trả OTP ra API để an toàn)
        return new ForgotPasswordResponse(commonMsg, null, expiresMinutes);
    }

    @Override
    @Transactional
    public String resetPassword(ResetPasswordRequest req) {

        Account acc = accountRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email không tồn tại"));

        // ✅ lấy OTP mới nhất (kể cả used=true)
        PasswordReset pr = passwordResetRepo.findTopByUserIdOrderByCreatedAtDesc(acc.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("OTP không tồn tại"));

        // ✅ 1) đã dùng
        if (pr.isUsed()) {
            throw new IllegalArgumentException("OTP đã được sử dụng");
        }


        // ✅ 2) hết hạn
        if (pr.getExpiresAt() != null && pr.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("OTP đã hết hạn");
        }

        // ✅ 3) sai OTP
        if (!encoder.matches(req.getToken(), pr.getToken())) {
            throw new IllegalArgumentException("OTP không hợp lệ");
        }

        // đổi mật khẩu
        acc.setPassword(encoder.encode(req.getNewPassword()));
        accountRepo.save(acc);

        // đánh dấu OTP đã dùng
        pr.setUsed(true);
        passwordResetRepo.save(pr);

        return "Đặt lại mật khẩu thành công";
    }

}
