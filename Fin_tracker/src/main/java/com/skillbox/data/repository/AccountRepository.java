package com.skillbox.data.repository;

import com.skillbox.data.model.dto.Account;
import java.util.List;
import java.util.Map;

/**
 * Интерфейс для чтения аккаунтов (счетов) пользователей
 */
public interface AccountRepository {

    /**
     * Читает все записи со счетами
     * @return список счетов
     */
    List<Account> readAll();

    Map<Integer, Account> readAllSortById();
}
