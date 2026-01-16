package com.rentalcar.controller;

import com.rentalcar.service.FptAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.rentalcar.service.OcrResultService;


@RestController
@RequestMapping("/api/ocr")
public class OcrController {

    private final FptAiService fptAiService;
    private final OcrResultService ocrResultService;

    public OcrController(FptAiService fptAiService, OcrResultService ocrResultService) {
        this.fptAiService = fptAiService;
        this.ocrResultService = ocrResultService;
    }

    @PostMapping("/vehicle-registration")
    public ResponseEntity<?> scanRegistration(@RequestParam("image") MultipartFile file) {
        try {
            String resultJson = fptAiService.scanVehicleRegistration(file);

            // ✅ lưu DB
            var saved = ocrResultService.saveFromFptJson("DLR", resultJson); // docType sửa theo bạn

            // ✅ trả về record đã lưu (hoặc trả rawJson tùy)
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi nhận dạng: " + e.getMessage());
        }
    }
}
