package com.asif.onlinebanking.online_banking_system.controller;



import com.asif.onlinebanking.online_banking_system.entity.Transaction;
import com.asif.onlinebanking.online_banking_system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public Transaction transferMoney(@RequestParam Long senderAccountId,
                                     @RequestParam Long receiverAccountId,
                                     @RequestParam BigDecimal amount,
                                     @RequestParam(required = false) String description) {
        return transactionService.transferMoney(senderAccountId, receiverAccountId, amount, description);
    }
}
