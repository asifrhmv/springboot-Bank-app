package com.asif.onlinebanking.online_banking_system.controller;



import com.asif.onlinebanking.online_banking_system.entity.Account;
import com.asif.onlinebanking.online_banking_system.entity.User;
import com.asif.onlinebanking.online_banking_system.repository.AccountRepository;
import com.asif.onlinebanking.online_banking_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public List<Account> getAccountsByUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getAccounts();
    }

    @GetMapping("/{accountId}")
    public Account getAccount(@PathVariable Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @GetMapping("/{accountId}/balance")
    public BigDecimal getBalance(@PathVariable Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }

    @PostMapping
    public Account createAccount(@RequestParam Long userId,
                                 @RequestParam String currency,
                                 @RequestParam BigDecimal initialBalance) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = Account.builder()
                .user(user)
                .currency(currency)
                .balance(initialBalance)
                .accountNumber("AZ" + System.currentTimeMillis()) // sadə unikal nömrə
                .build();

        return accountRepository.save(account);
    }
}
