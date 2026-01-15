package com.rentalcar.dto;

import java.util.List;

public class CarDetailDTO {

    private Long carId;
    private String modelName;
    private Integer year;
    private String status;
    private String rating;
    private String mainImage;
    private List<String> images;

    // ===== GETTERS =====

    public Long getCarId() {
        return carId;
    }

    public String getModelName() {
        return modelName;
    }

    public Integer getYear() {
        return year;
    }

    public String getStatus() {
        return status;
    }

    public String getRating() {
        return rating;
    }

    public String getMainImage() {
        return mainImage;
    }

    public List<String> getImages() {
        return images;
    }

    // ===== SETTERS =====

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
