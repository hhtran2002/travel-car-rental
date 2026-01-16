package com.rentalcar.controller.admin;

import com.rentalcar.entity.OwnerRegistration;
import com.rentalcar.service.OwnerRegistrationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/owner-registration")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOwnerRegistrationController {
  private final OwnerRegistrationService service;

  public AdminOwnerRegistrationController(OwnerRegistrationService service){
    this.service = service;
  }

  @GetMapping("/pending")
  public List<OwnerRegistration> pending(){
    return service.getPending();
  }

  @PostMapping("/{id}/approve")
  public OwnerRegistration approve(@PathVariable Long id, @RequestBody(required=false) Map<String,String> body){
    String note = body == null ? null : body.get("adminNote");
    return service.approve(id, note);
  }

  @PostMapping("/{id}/reject")
  public OwnerRegistration reject(@PathVariable Long id, @RequestBody(required=false) Map<String,String> body){
    String note = body == null ? null : body.get("adminNote");
    return service.reject(id, note);
  }
}
