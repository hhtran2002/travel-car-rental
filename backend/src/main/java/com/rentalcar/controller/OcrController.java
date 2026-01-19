package com.rentalcar.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentalcar.service.FptAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {

    private final FptAiService fptAiService;
    private final ObjectMapper mapper;

    public OcrController(FptAiService fptAiService, ObjectMapper mapper) {
        this.fptAiService = fptAiService;
        this.mapper = mapper;
    }

    @PostMapping("/vehicle-registration")
    public ResponseEntity<?> ocrVehicleRegistration(@RequestPart("image") MultipartFile image) throws Exception {
        String raw = fptAiService.ocrVehicleRegistration(image);

        // raw là JSON string -> parse ra Map
        Map<String, Object> parsedRaw = mapper.readValue(raw, new TypeReference<>() {});
        Map<String, Object> data = (Map<String, Object>) parsedRaw.getOrDefault("data", new LinkedHashMap<>());

        // Map field chuẩn hoá (tuỳ raw thực tế)
        Map<String, Object> parsed = new LinkedHashMap<>();
        parsed.put("plate", data.getOrDefault("plate", ""));
        parsed.put("owner", data.getOrDefault("owner", ""));
        parsed.put("frameNo", data.getOrDefault("frame_no", data.getOrDefault("frameNo", "")));
        parsed.put("engineNo", data.getOrDefault("engine_no", data.getOrDefault("engineNo", "")));

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("ok", true);
        res.put("raw", raw);
        res.put("parsed", parsed);

        return ResponseEntity.ok(res);
    }
}
