package com.rentalcar.service;

import com.rentalcar.config.FptAiProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FptAiService {

    private final FptAiProperties props;
    private final WebClient webClient = WebClient.builder().build();

    public FptAiService(FptAiProperties props) {
        this.props = props;
    }

    public String ocrDriverLicense(MultipartFile image) {
        // GPLX endpoint public: /vision/dlr/vnm
        return callFpt(props.getDriverLicenseUrl(), image, "driver_license");
    }

    public String ocrVehicleRegistration(MultipartFile image) {
        // Cà vẹt: nếu chưa có URL thật từ Reader Marketplace thì mock để chạy flow
        if (props.isMock() || props.getVehicleRegistrationUrl() == null || props.getVehicleRegistrationUrl().isBlank()) {
            return """
            { "mock": true, "docType":"vehicle_registration",
              "data": { "plate":"63AC-153.33", "owner":"NGUYEN DO QUANG MINH" }
            }
            """;
        }
        return callFpt(props.getVehicleRegistrationUrl(), image, "vehicle_registration");
    }

    private String callFpt(String url, MultipartFile image, String docType) {
        if (url == null || url.isBlank()) throw new IllegalStateException("Missing fpt.ai url for " + docType);
        if (props.getApiKey() == null || props.getApiKey().isBlank()) throw new IllegalStateException("Missing fpt.ai.api-key");

        try {
            MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();

            ByteArrayResource fileResource = new ByteArrayResource(image.getBytes()) {
                @Override public String getFilename() {
                    return (image.getOriginalFilename() != null) ? image.getOriginalFilename() : "image.jpg";
                }
            };

            form.add("image", fileResource);

            return webClient.post()
                    .uri(url)
                    .header("api_key", props.getApiKey())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .bodyValue(form)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e) {
            throw new RuntimeException("FPT.AI OCR failed (" + docType + "): " + e.getMessage(), e);
        }
    }
}
