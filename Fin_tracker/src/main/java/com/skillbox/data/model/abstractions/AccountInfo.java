package com.skillbox.data.model.abstractions;

import com.skillbox.data.model.enums.AccountType;

/**
 * Интерфейс для получения информации о счете.
 */
public interface AccountInfo {

    /**
     * Метод для получения уникального идентификатора счета.
     *
     * @return Уникальный идентификатор счета.
     */
    int getAccountId();

    /**
     * Метод для получения идентификатора пользователя, которому принадлежит счет.
     *
     * @return Идентификатор пользователя.
     */
    int getUserId();

    /**
     * Метод для получения типа счета.
     *
     * @return Имя счета.
     */
    AccountType getAccountType();
}
