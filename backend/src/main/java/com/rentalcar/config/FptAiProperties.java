package com.rentalcar.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fpt.ai")
public class FptAiProperties {
    private boolean mock = true;
    private String apiKey;

    // endpoints
    private String driverLicenseUrl = "https://api.fpt.ai/vision/dlr/vnm"; // GPLX
    private String idCardUrl = "https://api.fpt.ai/vision/idr/vnm";       // CCCD/CMND
    private String vehicleRegistrationUrl;                                 // Cà vẹt (cấu hình theo Reader Marketplace)

    public boolean isMock() { return mock; }
    public void setMock(boolean mock) { this.mock = mock; }

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public String getDriverLicenseUrl() { return driverLicenseUrl; }
    public void setDriverLicenseUrl(String driverLicenseUrl) { this.driverLicenseUrl = driverLicenseUrl; }

    public String getIdCardUrl() { return idCardUrl; }
    public void setIdCardUrl(String idCardUrl) { this.idCardUrl = idCardUrl; }

    public String getVehicleRegistrationUrl() { return vehicleRegistrationUrl; }
    public void setVehicleRegistrationUrl(String vehicleRegistrationUrl) { this.vehicleRegistrationUrl = vehicleRegistrationUrl; }
}
