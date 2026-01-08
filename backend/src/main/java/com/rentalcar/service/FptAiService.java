package com.rentalcar.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FptAiService {

    @Value("${fpt.ai.api.key}")
    private String apiKey;

    @Value("${fpt.ai.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String scanVehicleRegistration(MultipartFile file) throws IOException {
        // 1. Tạo Header chứa API Key
        HttpHeaders headers = new HttpHeaders();
        headers.set("api_key", apiKey);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 2. Chuẩn bị File để gửi đi
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        ByteArrayResource fileAsResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
        body.add("image", fileAsResource);

        // 3. Gọi API
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
            return response.getBody(); // Trả về JSON kết quả từ FPT
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi gọi FPT.AI: " + e.getMessage());
        }
    }
}