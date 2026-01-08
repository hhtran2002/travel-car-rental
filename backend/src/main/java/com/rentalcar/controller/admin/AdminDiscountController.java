package com.rentalcar.controller.admin;
import com.rentalcar.entity.Discount;
import com.rentalcar.repository.DiscountRepository; // Bạn tự tạo interface Repository nhé (extends JpaRepository)
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/api/admin/discounts")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDiscountController {
    // Để nhanh gọn, gọi thẳng Repository (với project nhỏ)
    private final DiscountRepository discountRepo;
    public AdminDiscountController(DiscountRepository r) { this.discountRepo = r; }

    @GetMapping
    public List<Discount> getAll() { return discountRepo.findAll(); }

    @PostMapping
    public Discount create(@RequestBody Discount d) { return discountRepo.save(d); }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { discountRepo.deleteById(id); }
}