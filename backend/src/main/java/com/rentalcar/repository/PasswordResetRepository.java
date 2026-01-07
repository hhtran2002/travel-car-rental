package com.rentalcar.repository;

import com.rentalcar.entity.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {

    // ✅ vô hiệu OTP cũ (used=true) khi tạo OTP mới
    @Modifying
    @Query("UPDATE PasswordReset pr SET pr.used = true WHERE pr.userId = :userId AND pr.used = false")
    int invalidateAllActiveByUserId(@Param("userId") Long userId);

    // ✅ lấy OTP còn hiệu lực mới nhất (used=false) để kiểm tra bằng BCrypt.matches
    Optional<PasswordReset> findTopByUserIdAndUsedFalseOrderByCreatedAtDesc(Long userId);

    // ✅ THÊM: lấy OTP mới nhất (dù used=true hay false)
    Optional<PasswordReset> findTopByUserIdOrderByCreatedAtDesc(Long userId);

    default Optional<PasswordReset> findLatestValidByUserId(Long userId) {
        return findTopByUserIdAndUsedFalseOrderByCreatedAtDesc(userId);
    }
}
