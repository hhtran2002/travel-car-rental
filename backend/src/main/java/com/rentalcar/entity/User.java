package com.rentalcar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "`user`") // vì "user" là từ khóa trong MySQL
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String phone;

    private LocalDate dob;

    private String gender;

    private String avatar;

    /**
     * ✅ Quan hệ 1-1 ngược lại với Account.
     * Account có: private User user;
     * mappedBy = "user" đúng theo tên field bên Account.
     *
     * Không bắt buộc phải có để query join chạy,
     * nhưng thêm để entity đầy đủ và dễ làm các chức năng admin.
     */
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Account account;
}
