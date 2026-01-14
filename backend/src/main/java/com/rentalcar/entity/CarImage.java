package com.rentalcar.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "car_image")
public class CarImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "link_image", nullable = false)
    private String linkImage;

    @Column(name = "car_id", nullable = false)
    private Long carId;

    // ====== CONSTRUCTOR ======
    public CarImage() {
    }

    public CarImage(Long imageId, String linkImage, Long carId) {
        this.imageId = imageId;
        this.linkImage = linkImage;
        this.carId = carId;
    }

    // ====== GETTER & SETTER ======

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}
