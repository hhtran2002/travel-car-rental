// src/main/java/com/rentalcar/service/OcrResultService.java
package com.rentalcar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentalcar.dto.FptOcrResponse;
import com.rentalcar.entity.OcrResult;
import com.rentalcar.repository.OcrResultRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OcrResultService {

    private final OcrResultRepository repo;
    private final ObjectMapper objectMapper;

    public OcrResultService(OcrResultRepository repo, ObjectMapper objectMapper) {
        this.repo = repo;
        this.objectMapper = objectMapper;
    }

    public OcrResult saveFromFptJson(String docType, String rawJson) throws Exception {
        FptOcrResponse parsed = objectMapper.readValue(rawJson, FptOcrResponse.class);

        OcrResult r = new OcrResult();
        r.setDocType(docType);
        r.setRawJson(rawJson);
        r.setErrorCode(parsed.getErrorCode());
        r.setErrorMessage(parsed.getErrorMessage());
        r.setCreatedAt(LocalDateTime.now());

        return repo.save(r);
    }
}
