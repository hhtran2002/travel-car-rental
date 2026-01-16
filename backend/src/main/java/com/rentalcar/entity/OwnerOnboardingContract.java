package com.rentalcar.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "owner_onboarding_contract")
public class OwnerOnboardingContract {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private Long userId;
    @Column(nullable = false) private Long ownerRegistrationId;
    @Column(nullable = false) private String status;

    private String pdfPath;

    @Column(columnDefinition = "TEXT")
    private String signatureBase64;

    private String signatureId;
    private String fileHashBase64;
    private String adminNote;

    private LocalDateTime createdAt;
    private LocalDateTime signedAt;
    private LocalDateTime reviewedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getOwnerRegistrationId() { return ownerRegistrationId; }
    public void setOwnerRegistrationId(Long ownerRegistrationId) { this.ownerRegistrationId = ownerRegistrationId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPdfPath() { return pdfPath; }
    public void setPdfPath(String pdfPath) { this.pdfPath = pdfPath; }

    public String getSignatureBase64() { return signatureBase64; }
    public void setSignatureBase64(String signatureBase64) { this.signatureBase64 = signatureBase64; }

    public String getSignatureId() { return signatureId; }
    public void setSignatureId(String signatureId) { this.signatureId = signatureId; }

    public String getFileHashBase64() { return fileHashBase64; }
    public void setFileHashBase64(String fileHashBase64) { this.fileHashBase64 = fileHashBase64; }

    public String getAdminNote() { return adminNote; }
    public void setAdminNote(String adminNote) { this.adminNote = adminNote; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getSignedAt() { return signedAt; }
    public void setSignedAt(LocalDateTime signedAt) { this.signedAt = signedAt; }

    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
}
