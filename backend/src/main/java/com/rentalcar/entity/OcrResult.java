// src/main/java/com/rentalcar/entity/OcrResult.java
package com.rentalcar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ocr_results")
@Data

@AllArgsConstructor
@Builder
public class OcrResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String docType;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String rawJson;

    private Integer errorCode;
    private String errorMessage;

    private LocalDateTime createdAt;

    public OcrResult() {}

    public OcrResult(String docType, String rawJson, Integer errorCode, String errorMessage, LocalDateTime createdAt) {
        this.docType = docType;
        this.rawJson = rawJson;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getDocType() { return docType; }
    public void setDocType(String docType) { this.docType = docType; }

    public String getRawJson() { return rawJson; }
    public void setRawJson(String rawJson) { this.rawJson = rawJson; }

    public Integer getErrorCode() { return errorCode; }
    public void setErrorCode(Integer errorCode) { this.errorCode = errorCode; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
