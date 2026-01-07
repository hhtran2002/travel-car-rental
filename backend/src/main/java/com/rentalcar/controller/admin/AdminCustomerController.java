package com.rentalcar.controller.admin;

import com.rentalcar.dto.admin.CustomerCreateRequest;
import com.rentalcar.dto.admin.CustomerResponse;
import com.rentalcar.service.AdminCustomerService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.rentalcar.dto.admin.CustomerUpdateRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/admin/customers")
@PreAuthorize("hasRole('ADMIN')")


public class AdminCustomerController {

    private final AdminCustomerService adminCustomerService;

    public AdminCustomerController(AdminCustomerService adminCustomerService) {
        this.adminCustomerService = adminCustomerService;
    }

    @GetMapping
    public Page<CustomerResponse> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return adminCustomerService.getCustomers(keyword, page, size);
    }

    @GetMapping("/{id}")
    public CustomerResponse detail(@PathVariable Long id) {
        return adminCustomerService.getCustomerDetail(id);
    }

    @PostMapping
    public CustomerResponse create(@Valid @RequestBody CustomerCreateRequest request) {
        return adminCustomerService.createCustomer(request);
    }

    @PutMapping("/{id}")
    public CustomerResponse update(@PathVariable Long id,
                                   @Valid @RequestBody(required = false) CustomerUpdateRequest request) {
        if (request == null) throw new IllegalArgumentException("Body không được trống");
        return adminCustomerService.updateCustomer(id, request);
    }



}
