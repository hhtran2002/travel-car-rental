package com.rentalcar.service.impl;

import com.rentalcar.dto.admin.CustomerCreateRequest;
import com.rentalcar.dto.admin.CustomerResponse;
import com.rentalcar.dto.admin.CustomerUpdateRequest;
import com.rentalcar.entity.Account;
import com.rentalcar.entity.Role;
import com.rentalcar.entity.User;
import com.rentalcar.exception.ConflictException;
import com.rentalcar.exception.NotFoundException;
import com.rentalcar.repository.AccountRepository;
import com.rentalcar.repository.RoleRepository;
import com.rentalcar.repository.UserRepository;
import com.rentalcar.service.AdminCustomerService;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminCustomerServiceImpl implements AdminCustomerService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminCustomerServiceImpl(AccountRepository accountRepository,
                                    UserRepository userRepository,
                                    RoleRepository roleRepository,
                                    PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<CustomerResponse> getCustomers(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("userId").descending());
        String kw = (keyword == null || keyword.trim().isEmpty()) ? null : keyword.trim();

        return accountRepository.searchCustomers(kw, pageable)
                .map(this::toResponse);
    }

    private CustomerResponse toResponse(Account a) {
        CustomerResponse res = new CustomerResponse();
        res.setUserId(a.getUserId());
        res.setEmail(a.getEmail());
        res.setStatus(a.getStatus().toString()); // active/locked

        User u = a.getUser();
        if (u != null) {
            res.setFullName(u.getFullName());
            res.setPhone(u.getPhone());
            res.setDob(u.getDob());
            res.setGender(u.getGender());
            res.setAvatar(u.getAvatar());
        }
        return res;
    }

    private void ensureCustomer(Account a) {
        if (a.getRole() == null || a.getRole().getRoleName() == null
                || !"customer".equalsIgnoreCase(a.getRole().getRoleName())) {
            throw new IllegalArgumentException("Tài khoản không phải khách hàng");
        }
    }

    @Override
    public CustomerResponse getCustomerDetail(Long id) {
        Account a = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khách hàng"));

        ensureCustomer(a);
        return toResponse(a);
    }

    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerCreateRequest request) {

        // 1) Check email trùng
        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email đã tồn tại");
        }

        // 2) Lấy role customer
        Role customerRole = roleRepository.findByRoleName("customer")
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy role customer"));

        // 3) Tạo user
        User user = new User();
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setDob(request.getDob());
        user.setGender(request.getGender());
        user.setAvatar(request.getAvatar());

        user = userRepository.save(user);

        // 4) Tạo account
        Account account = new Account();
        account.setUser(user); // nếu entity Account dùng @MapsId thì OK
        account.setEmail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setStatus(Account.Status.active);
        account.setCreatedAt(java.time.LocalDateTime.now());
        account.setRole(customerRole);

        account = accountRepository.save(account);

        return toResponse(account);
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerUpdateRequest request) {

        Account a = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khách hàng"));

        ensureCustomer(a);

        User u = a.getUser();
        if (u == null) {
            // trường hợp dữ liệu lỗi (account không gắn user)
            throw new NotFoundException("Không tìm thấy thông tin user của khách hàng");
        }

        // Update các field của user nếu client gửi lên (null thì giữ nguyên)
        if (request.getFullName() != null) u.setFullName(request.getFullName());
        if (request.getPhone() != null) u.setPhone(request.getPhone());
        if (request.getDob() != null) u.setDob(request.getDob());
        if (request.getGender() != null) u.setGender(request.getGender());
        if (request.getAvatar() != null) u.setAvatar(request.getAvatar());

        // Update status account nếu có (DTO đã @Pattern active|locked)
        if (request.getStatus() != null) {
            String st = request.getStatus().trim().toLowerCase();
            // valueOf cần đúng enum name, enum của bạn là active/locked -> ok
            a.setStatus(Account.Status.valueOf(st));
        }

        // save cho rõ ràng
        userRepository.save(u);
        accountRepository.save(a);

        return toResponse(a);
    }
}
