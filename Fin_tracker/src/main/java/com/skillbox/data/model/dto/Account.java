package com.skillbox.data.model.dto;

import com.skillbox.data.model.abstractions.AccountInfo;
import com.skillbox.data.model.abstractions.AccountStatement;
import com.skillbox.data.model.abstractions.BalanceOperations;
import com.skillbox.data.model.dto.transactions.Transaction;
import com.skillbox.data.model.enums.AccountType;
import lombok.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс представляющий собой счет в банке
 */
@Data
@AllArgsConstructor
@Builder
public class Account implements AccountInfo, BalanceOperations, AccountStatement {

    private final List<Transaction> transactions = new LinkedList<>();

    private int accountId;
    private int userId;
    private AccountType accountType;
    private BigDecimal balance;

    @Override
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
