package com.rentalcar.controller;

import com.rentalcar.service.EsignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/esign")
@RequiredArgsConstructor
public class EsignController {

    private final EsignService esignService;

    @PostMapping(value = "/sign", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> sign(
            @RequestPart("file") MultipartFile file,
            @RequestParam(value = "reason", required = false) String reason
    ) {
        return ResponseEntity.ok(esignService.sign(file, reason));
    }
}
