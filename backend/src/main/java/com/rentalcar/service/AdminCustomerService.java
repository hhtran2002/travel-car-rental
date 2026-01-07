package com.rentalcar.service;


import com.rentalcar.dto.admin.CustomerCreateRequest;
import com.rentalcar.dto.admin.CustomerUpdateRequest;
import com.rentalcar.dto.admin.CustomerResponse;
import org.springframework.data.domain.Page;

public interface AdminCustomerService {
    Page<CustomerResponse> getCustomers(String keyword, int page, int size);
    CustomerResponse getCustomerDetail(Long id);
    CustomerResponse createCustomer(CustomerCreateRequest request);
    CustomerResponse updateCustomer(Long id, CustomerUpdateRequest request);


}

