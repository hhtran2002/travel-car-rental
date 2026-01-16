package com.rentalcar.service;

import com.rentalcar.entity.Booking;
import com.rentalcar.entity.Contract;
import com.rentalcar.repository.ContractRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContractService {

    private final ContractRepository contractRepo;

    public ContractService(ContractRepository contractRepo) {
        this.contractRepo = contractRepo;
    }

    // ================= TẠO HỢP ĐỒNG KHI CONFIRM BOOKING =================
    public Contract createContractForBooking(Booking booking) {

        // Validate: 1 booking chỉ có 1 contract
        contractRepo.findByBookingId(booking.getBookingId())
                .ifPresent(c -> {
                    throw new RuntimeException("Booking đã có hợp đồng");
                });

        Contract contract = new Contract();
        contract.setBookingId(booking.getBookingId());
        contract.setCreatedDate(LocalDateTime.now());
        contract.setStatus("pending");
        contract.setSignedBy("customer");

        return contractRepo.save(contract);
    }

    // ================= LẤY HỢP ĐỒNG THEO BOOKING =================
    public Contract getByBookingId(Long bookingId) {
        return contractRepo.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hợp đồng"));
    }
}
