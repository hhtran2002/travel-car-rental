package com.rentalcar.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle_registration_docs")
public class VehicleRegistrationDoc {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plate;
    private String owner;

    @Column(name="frame_no")
    private String frameNo;

    @Column(name="engine_no")
    private String engineNo;

    @Lob
    @Column(name="raw_json")
    private String rawJson;

    @Column(name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // getters/setters
    public Long getId() { return id; }

    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getFrameNo() { return frameNo; }
    public void setFrameNo(String frameNo) { this.frameNo = frameNo; }

    public String getEngineNo() { return engineNo; }
    public void setEngineNo(String engineNo) { this.engineNo = engineNo; }

    public String getRawJson() { return rawJson; }
    public void setRawJson(String rawJson) { this.rawJson = rawJson; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
