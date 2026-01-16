package com.rentalcar.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FptAiService {

    @Value("${fpt.ai.api-key}")
    private String apiKey;

    @Value("${fpt.ai.url}")
    private String apiUrl; // ✅ tên chuẩn

    private final RestTemplate restTemplate = new RestTemplate();

    public String scanVehicleRegistration(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("api_key", apiKey); 

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response =
                    restTemplate.postForEntity(apiUrl, requestEntity, String.class); // ✅ dùng apiUrl
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            throw new RuntimeException("FPT.AI " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString());
        }
    }
}
