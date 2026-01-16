package com.rentalcar.service;

import com.rentalcar.entity.Contract;
import com.rentalcar.repository.ContractRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class DigitalSignatureService {

    private final ContractRepository contractRepository;

    public DigitalSignatureService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    // Hàm thực hiện ký số (Mô phỏng)
    public Contract signContract(Long contractId, String signerName) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Hợp đồng không tồn tại"));

        
        
        String fakeSignedUrl = "https://server-storage.com/contracts/signed_" + contractId + ".pdf";
        System.out.println("Đang gọi API ký số cho hợp đồng ID: " + contractId);
        System.out.println("Người ký: " + signerName);
        // --- KẾT THÚC LOGIC GIẢ LẬP ---

        // Cập nhật trạng thái hợp đồng sau khi ký thành công
        contract.setSignedBy(signerName);
        contract.setStatus("signed"); // Hoặc "active" tùy logic của bạn
        // contract.setDocumentUrl(fakeSignedUrl); // Bỏ comment nếu đã thêm cột này vào DB
        
        return contractRepository.save(contract);
    }
}