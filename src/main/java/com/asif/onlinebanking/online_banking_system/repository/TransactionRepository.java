package com.asif.onlinebanking.online_banking_system.repository;


import com.asif.onlinebanking.online_banking_system.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
