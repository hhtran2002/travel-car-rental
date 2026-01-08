package com.rentalcar.controller;

import com.rentalcar.service.FptAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {

    private final FptAiService fptAiService;

    public OcrController(FptAiService fptAiService) {
        this.fptAiService = fptAiService;
    }

    // API Upload ảnh giấy tờ xe để nhận dạng
    @PostMapping("/vehicle-registration")
    public ResponseEntity<?> scanRegistration(@RequestParam("image") MultipartFile file) {
        try {
            String resultJson = fptAiService.scanVehicleRegistration(file);
            return ResponseEntity.ok(resultJson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi nhận dạng: " + e.getMessage());
        }
    }
}