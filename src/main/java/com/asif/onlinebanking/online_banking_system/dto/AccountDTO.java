package com.asif.onlinebanking.online_banking_system.dto;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private String currency;
}
