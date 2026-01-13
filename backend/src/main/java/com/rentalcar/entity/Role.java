package com.rentalcar.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name", nullable = false)
    private String roleName; // admin / driver / customer

    // ================= CONSTRUCTOR =================
    public Role() {
    }

    // ================= GETTERS =================
    public Long getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    // ================= SETTERS =================
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
