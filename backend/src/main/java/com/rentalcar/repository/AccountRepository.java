package com.rentalcar.repository;

import com.rentalcar.entity.Account;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    /**
     * ✅ Admin list/search khách hàng (role = customer)
     * search theo fullName / email / phone
     */
    @EntityGraph(attributePaths = {"user", "role"})
    @Query("""
        select a
        from Account a
        join a.user u
        join a.role r
        where r.roleName = 'customer'
          and (
                :keyword is null
                or lower(u.fullName) like lower(concat('%', :keyword, '%'))
                or lower(a.email) like lower(concat('%', :keyword, '%'))
                or lower(u.phone) like lower(concat('%', :keyword, '%'))
          )
        """)
    Page<Account> searchCustomers(@Param("keyword") String keyword, Pageable pageable);
}
