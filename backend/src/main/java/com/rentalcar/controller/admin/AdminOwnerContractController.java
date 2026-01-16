package com.rentalcar.controller.admin;

import com.rentalcar.entity.OwnerOnboardingContract;
import com.rentalcar.repository.OwnerOnboardingContractRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/owner-contracts")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOwnerContractController {

    private final OwnerOnboardingContractRepository repo;

    public AdminOwnerContractController(OwnerOnboardingContractRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/pending")
    public List<OwnerOnboardingContract> pending() {
        return repo.findByStatus("PENDING_ADMIN");
    }

    @PostMapping("/{id}/approve")
    public OwnerOnboardingContract approve(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body
    ) {
        OwnerOnboardingContract c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        if (!"PENDING_ADMIN".equals(c.getStatus())) {
            throw new RuntimeException("Chỉ duyệt hợp đồng PENDING_ADMIN");
        }

        c.setStatus("APPROVED");
        c.setAdminNote(body == null ? null : body.get("adminNote"));
        c.setReviewedAt(LocalDateTime.now());
        return repo.save(c);
    }

    @PostMapping("/{id}/reject")
    public OwnerOnboardingContract reject(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body
    ) {
        OwnerOnboardingContract c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        if (!"PENDING_ADMIN".equals(c.getStatus())) {
            throw new RuntimeException("Chỉ từ chối hợp đồng PENDING_ADMIN");
        }

        c.setStatus("REJECTED");
        c.setAdminNote(body == null ? null : body.get("adminNote"));
        c.setReviewedAt(LocalDateTime.now());
        return repo.save(c);
    }
}
