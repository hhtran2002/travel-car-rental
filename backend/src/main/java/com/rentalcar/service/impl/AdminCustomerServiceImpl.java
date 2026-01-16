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

        // Check phone trùng
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new ConflictException("Số điện thoại đã tồn tại");
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
        if (request.getFullName() == null &&
                request.getPhone() == null &&
                request.getDob() == null &&
                request.getGender() == null &&
                request.getAvatar() == null &&
                request.getStatus() == null) {
            throw new IllegalArgumentException("Không có dữ liệu cập nhật");
        }


        Account a = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khách hàng"));

        ensureCustomer(a);

        User u = a.getUser();
        if (u == null) {
            // trường hợp dữ liệu lỗi (account không gắn user)
            throw new NotFoundException("Không tìm thấy thông tin user của khách hàng");
        }


        // fullName: nếu có gửi -> trim -> rỗng thì báo lỗi
        if (request.getFullName() != null) {
            String name = request.getFullName().trim();
            if (name.isEmpty()) throw new IllegalArgumentException("Họ tên không được trống");
            u.setFullName(name);
        }

        // phone: nếu có gửi -> trim -> rỗng lỗi, trùng lỗi 409
        if (request.getPhone() != null) {
            String phone = request.getPhone().trim();
            if (phone.isEmpty()) throw new IllegalArgumentException("Số điện thoại không được trống");

            // ✅ check trùng loại trừ chính nó
            if (userRepository.existsByPhoneAndUserIdNot(phone, u.getUserId())) {
                throw new ConflictException("Số điện thoại đã tồn tại");
            }
            u.setPhone(phone);
        }

        // dob: nếu gửi lên thì update (Past validate đã bắt)
        if (request.getDob() != null) {
            u.setDob(request.getDob());
        }

        // gender: nếu gửi "" thì xóa, còn không thì set (Pattern sẽ validate "nam|nữ")
        if (request.getGender() != null) {
            String g = request.getGender().trim().toLowerCase();
            if (g.isEmpty()) u.setGender(null);
            else u.setGender(g);
        }

       // avatar: "" => xóa
        if (request.getAvatar() != null) {
            String av = request.getAvatar().trim();
            u.setAvatar(av.isEmpty() ? null : av);
        }

        // status: nếu có gửi -> không cho rỗng
        if (request.getStatus() != null) {
            String st = request.getStatus().trim().toLowerCase();
            if (st.isEmpty()) throw new IllegalArgumentException("Status không được trống");
            a.setStatus(Account.Status.valueOf(st));

        }


        // save cho rõ ràng
        userRepository.save(u);
        accountRepository.save(a);

        return toResponse(a);
    }
}
