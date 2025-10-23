package com.asif.onlinebanking.online_banking_system.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
    private Long id;
    private Long senderAccountId;
    private Long receiverAccountId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String description;
}
