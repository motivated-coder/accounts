package com.skd.accounts.repository;

import com.skd.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Accounts,Long> {
    Optional<Accounts> findByCustomerId(Long customerId);

    void deleteByCustomerId(Long customerId);
}
