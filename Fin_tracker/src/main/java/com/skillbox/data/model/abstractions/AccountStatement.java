package com.skillbox.data.model.abstractions;

import com.skillbox.data.model.dto.transactions.Transaction;

import java.util.List;

/**
 * Интерфейс для управления выписками по счету.
 */
public interface AccountStatement {

    /**
     * Метод для получения списка всех транзакций по счету.
     *
     * @return Список транзакций.
     */
    List<Transaction> getTransactions();

}
