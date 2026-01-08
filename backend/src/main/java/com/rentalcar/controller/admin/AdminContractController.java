package com.rentalcar.controller.admin;

import com.rentalcar.entity.Booking;
import com.rentalcar.entity.Contract;
import com.rentalcar.repository.BookingRepository;
import com.rentalcar.repository.ContractRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/contracts")
@PreAuthorize("hasRole('ADMIN')")
public class AdminContractController {

    private final ContractRepository contractRepository;
    private final BookingRepository bookingRepository;

    public AdminContractController(ContractRepository contractRepository, BookingRepository bookingRepository) {
        this.contractRepository = contractRepository;
        this.bookingRepository = bookingRepository;
    }

    
    @GetMapping
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    
    @PostMapping("/create/{bookingId}")
    public ResponseEntity<?> createContract(@PathVariable Long bookingId, 
                                            @RequestParam String signedBy) {
        
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn đặt xe"));

        
        if (contractRepository.findByBookingId(bookingId).isPresent()) {
            return ResponseEntity.badRequest().body("Đơn này đã có hợp đồng rồi!");
        }

        Contract contract = new Contract();
        contract.setBookingId(bookingId);
        contract.setTotalPrice(booking.getTotalPrice()); // Lấy giá từ booking
        contract.setCreatedDate(LocalDateTime.now());
        contract.setStatus("active");
        contract.setSignedBy(signedBy); // Tên người ký (Admin nhập vào)
        contract.setDetails("Hợp đồng thuê xe cho đơn #" + bookingId);

        return ResponseEntity.ok(contractRepository.save(contract));
    }

    // 3. Kết thúc hợp đồng (Khi khách trả xe)
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Contract> completeContract(@PathVariable Long id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hợp đồng không tồn tại"));

        contract.setStatus("completed");
        contract.setReturnDate(LocalDateTime.now());
        
        return ResponseEntity.ok(contractRepository.save(contract));
    }
}