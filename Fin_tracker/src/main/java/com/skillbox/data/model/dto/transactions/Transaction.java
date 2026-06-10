package com.skillbox.data.model.dto.transactions;

import com.skillbox.data.model.dto.Account;
import com.skillbox.data.model.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Абстрактный класс, представляющий собой транзакцию
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction  {
    private final TransactionType type;

    private int accountId;
    private int transactionId;
    private LocalDateTime dateTime;
    private String category;
    private BigDecimal amount;

    private Account account;
}
