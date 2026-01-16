package com.rentalcar.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "`user`") // vì "user" là từ khóa trong MySQL
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String phone;

    private LocalDate dob;

    private String gender;

    private String avatar;

    /**
     * Quan hệ 1-1 ngược lại với Account
     */
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Account account;

    // ================= CONSTRUCTOR =================
    public User() {
    }

    // ================= GETTERS =================
    public Long getUserId() {
        return userId;
    }

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

    public Account getAccount() {
        return account;
    }

    // ================= SETTERS =================
    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public void setAccount(Account account) {
        this.account = account;
    }
    
}
