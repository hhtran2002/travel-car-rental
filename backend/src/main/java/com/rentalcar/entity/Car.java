package com.rentalcar.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long carId;

    @Column(name = "plate_number", length = 20, nullable = false)
    private String plateNumber;

    @Column(name = "brand_id", nullable = false)
    private Long brandId;

    @Column(name = "type_id", nullable = false)
    private Long typeId;

    @Column(name = "model_name", nullable = false)
    private String modelName;

    @Column(name = "year")
    private Integer year;

    // DB enum: 'available','maintenance','inactive' -> để String cho nhanh, đỡ lỗi enum
    @Column(name = "status", nullable = false, columnDefinition = "enum('available','maintenance','inactive')")
    private String status;

    @Column(name = "rating", length = 20)
    private String rating;

    @Column(name = "main_image")
    private String mainImage;

    // getters/setters
    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }

    public Long getTypeId() { return typeId; }
    public void setTypeId(Long typeId) { this.typeId = typeId; }

    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getMainImage() { return mainImage; }
    public void setMainImage(String mainImage) { this.mainImage = mainImage; }
}
