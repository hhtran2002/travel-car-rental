package com.rentalcar.service.impl;

import com.rentalcar.entity.Account;
import com.rentalcar.entity.OwnerRegistration;
import com.rentalcar.entity.Role;
import com.rentalcar.entity.User;
import com.rentalcar.repository.AccountRepository;
import com.rentalcar.repository.OwnerRegistrationRepository;
import com.rentalcar.repository.RoleRepository;
import com.rentalcar.repository.UserRepository;
import com.rentalcar.service.OwnerRegistrationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class OwnerRegistrationServiceImpl implements OwnerRegistrationService {

    private final OwnerRegistrationRepository repo;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;

    private final Path uploadRoot = Paths.get("uploads", "owner-registration");

    public OwnerRegistrationServiceImpl(
            OwnerRegistrationRepository repo,
            UserRepository userRepository,
            RoleRepository roleRepository,
            AccountRepository accountRepository
    ) {
        this.repo = repo;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public OwnerRegistration register(OwnerRegistration req) {
        // Nếu vẫn giữ method này, KHÔNG cho FE gửi userId (hở security)
        if (req.getUserId() == null) throw new RuntimeException("Thiếu userId");
        repo.findByUserId(req.getUserId()).ifPresent(r -> { throw new RuntimeException("User đã gửi đăng ký trước đó"); });
        if (req.getStatus() == null) req.setStatus("PENDING");
        if (req.getCreatedAt() == null) req.setCreatedAt(LocalDateTime.now());
        return repo.save(req);
    }

    @Override
    @Transactional
    public OwnerRegistration approve(Long id, String adminNote) {
        OwnerRegistration reg = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ"));

        if (!"PENDING".equals(reg.getStatus())) {
            throw new RuntimeException("Hồ sơ đã được xử lý");
        }

        reg.setStatus("APPROVED");
        reg.setReviewedAt(LocalDateTime.now());
        reg.setAdminNote(adminNote);

        User user = userRepository.findById(reg.getUserId())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        Account acc = user.getAccount();
        if (acc == null) throw new RuntimeException("User chưa có account");

        // QUAN TRỌNG: roleName trong DB nhiều khả năng là 'owner' (lowercase)
        Role ownerRole = roleRepository.findByRoleNameIgnoreCase("owner")
                .orElseThrow(() -> new RuntimeException("Role owner not found"));

        acc.setRole(ownerRole);
        accountRepository.save(acc);

        return repo.save(reg);
    }

    @Override
    public OwnerRegistration reject(Long id, String adminNote) {
        OwnerRegistration reg = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ"));

        if (!"PENDING".equals(reg.getStatus())) {
            throw new RuntimeException("Hồ sơ đã được xử lý");
        }

        reg.setStatus("REJECTED");
        reg.setReviewedAt(LocalDateTime.now());
        reg.setAdminNote(adminNote);

        return repo.save(reg);
    }

    @Override
    public OwnerRegistration getByUser(Long userId) {
        return repo.findByUserId(userId).orElse(null);
    }

    @Override
    public List<OwnerRegistration> getPending() {
        return repo.findByStatus("PENDING");
    }

    @Override
    public OwnerRegistration registerMultipart(Long userId, String fullName, String phone,
            String carBrand, String carModel, String licensePlate, String note,
            MultipartFile cccdFront, MultipartFile cavet) {

        if (userId == null) throw new RuntimeException("Unauthenticated");
        if (fullName == null || fullName.isBlank()) throw new RuntimeException("Thiếu fullName");
        if (phone == null || phone.isBlank()) throw new RuntimeException("Thiếu phone");
        if (carBrand == null || carBrand.isBlank()) throw new RuntimeException("Thiếu carBrand");
        if (carModel == null || carModel.isBlank()) throw new RuntimeException("Thiếu carModel");
        if (licensePlate == null || licensePlate.isBlank()) throw new RuntimeException("Thiếu licensePlate");
        if (cccdFront == null || cccdFront.isEmpty()) throw new RuntimeException("Thiếu file cccdFront");
        if (cavet == null || cavet.isEmpty()) throw new RuntimeException("Thiếu file cavet");

        repo.findByUserId(userId).ifPresent(r -> { throw new RuntimeException("User đã gửi đăng ký trước đó"); });

        String cccdPath = saveFile(userId, cccdFront, "cccdFront");
        String cavetPath = saveFile(userId, cavet, "cavet");

        String docsJson = """
            {"cccdFront":"%s","cavet":"%s","note":"%s"}
            """.formatted(cccdPath, cavetPath, note == null ? "" : note.replace("\"", "\\\""));

        OwnerRegistration reg = new OwnerRegistration();
        reg.setUserId(userId);
        reg.setFullName(fullName);
        reg.setPhone(phone);
        reg.setCarBrand(carBrand);
        reg.setCarModel(carModel);
        reg.setLicensePlate(licensePlate);
        reg.setDocuments(docsJson);
        reg.setStatus("PENDING");
        reg.setCreatedAt(LocalDateTime.now());

        return repo.save(reg);
    }

    private String saveFile(Long userId, MultipartFile file, String field) {
        try {
            String original = Objects.requireNonNullElse(file.getOriginalFilename(), "file");
            String ext = "";
            int dot = original.lastIndexOf('.');
            if (dot >= 0) ext = original.substring(dot);

            String name = field + "_" + UUID.randomUUID() + ext;
            Path dir = uploadRoot.resolve(String.valueOf(userId));
            Files.createDirectories(dir);

            Path target = dir.resolve(name).normalize();
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            // trả về path public (mình sẽ map /uploads/** trong WebMvc config)
            return "/uploads/owner-registration/" + userId + "/" + name;
        } catch (Exception e) {
            throw new RuntimeException("Upload file failed: " + e.getMessage(), e);
        }
    }
}
