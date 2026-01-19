package com.rentalcar.controller;

import com.rentalcar.dto.VehicleRegistrationSaveRequest;
import com.rentalcar.entity.VehicleRegistrationDoc;
import com.rentalcar.repository.VehicleRegistrationDocRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/docs")
public class VehicleDocController {

    private final VehicleRegistrationDocRepository repo;

    public VehicleDocController(VehicleRegistrationDocRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/vehicle-registration")
    public ResponseEntity<?> saveVehicleRegistration(@RequestBody VehicleRegistrationSaveRequest req) {
        VehicleRegistrationDoc doc = new VehicleRegistrationDoc();
        doc.setPlate(req.plate);
        doc.setOwner(req.owner);
        doc.setFrameNo(req.frameNo);
        doc.setEngineNo(req.engineNo);
        doc.setRawJson(req.rawJson);

        VehicleRegistrationDoc saved = repo.save(doc);
        return ResponseEntity.ok(Map.of("ok", true, "id", saved.getId()));
    }
}
