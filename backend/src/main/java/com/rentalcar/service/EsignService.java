package com.rentalcar.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
public class EsignService {

    @Value("${esign.api.url}")
    private String esignUrl;

    @Value("${esign.api.token}")
    private String esignToken;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> sign(MultipartFile file, String reason) {
        try {
            // 1) Build multipart request gửi sang provider local/demo
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            });

            // ✅ chỉ add reason 1 lần (đừng vừa query param vừa form-data)
            if (reason != null && !reason.isBlank()) {
                body.add("reason", reason.trim());
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // ✅ Provider local không cần token thì vẫn gửi cũng không sao
            // Nếu provider demo thật cần Bearer token thì để dòng này
            if (esignToken != null && !esignToken.isBlank()) {
                headers.setBearerAuth(esignToken.trim());
            }

            HttpEntity<MultiValueMap<String, Object>> req = new HttpEntity<>(body, headers);

            // 2) Call provider
            ResponseEntity<Map> resp = restTemplate.postForEntity(esignUrl, req, Map.class);

            // 3) Trả về client
            return Map.of(
                    "ok", true,
                    "provider", "DEMO",
                    "status", resp.getStatusCodeValue(),
                    "data", resp.getBody()
            );

        } catch (Exception e) {
            // ✅ Nếu provider lỗi: trả lỗi rõ ràng (không giả ok)
            return Map.of(
                    "ok", false,
                    "provider", "DEMO",
                    "status", 502,
                    "message", "Không gọi được provider e-sign",
                    "detail", e.getMessage(),
                    "signedAt", Instant.now().toString(),
                    "traceId", UUID.randomUUID().toString()
            );
        }
    }
}
