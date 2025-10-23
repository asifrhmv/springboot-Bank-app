package com.asif.onlinebanking.online_banking_system.service;


import com.asif.onlinebanking.online_banking_system.entity.Transaction;

import java.math.BigDecimal;

public interface TransactionService {
    Transaction transferMoney(Long senderAccountId, Long receiverAccountId, BigDecimal amount, String description);
}
