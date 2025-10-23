package com.asif.onlinebanking.online_banking_system.service;


import com.asif.onlinebanking.online_banking_system.entity.Account;
import com.asif.onlinebanking.online_banking_system.entity.Transaction;
import com.asif.onlinebanking.online_banking_system.repository.AccountRepository;
import com.asif.onlinebanking.online_banking_system.repository.TransactionRepository;
import com.asif.onlinebanking.online_banking_system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Transaction transferMoney(Long senderAccountId, Long receiverAccountId, BigDecimal amount, String description) {

        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        Account sender = accountRepository.findById(senderAccountId)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Account receiver = accountRepository.findById(receiverAccountId)
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        if(sender.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Insufficient balance");
        }


        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);


        Transaction transaction = Transaction.builder()
                .senderAccount(sender)
                .receiverAccount(receiver)
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .description(description)
                .build();

        return transactionRepository.save(transaction);
    }
}
