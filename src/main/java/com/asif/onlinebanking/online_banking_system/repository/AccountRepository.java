package com.asif.onlinebanking.online_banking_system.repository;

import com.asif.onlinebanking.online_banking_system.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
