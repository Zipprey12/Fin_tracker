package com.skillbox.data.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum AccountType {

    /**
     * Текущий счет обычно используется для повседневных финансовых операций, таких как оплата счетов, покупки и
     * переводы.
     */
    CHECKING(0, "Текущий"),

    /**
     * Сберегательный счет предназначен для хранения сбережений и накоплений. Обычно он имеет ограниченное количество
     * транзакций в месяц, но предлагает процентный доход на сбережения.
     */
    SAVINGS(1, "Сберегательный"),

    /**
     * Кредитный счет представляет собой счет, на котором отражаются операции, связанные с использованием кредита.
     * Кредитный лимит и процентные ставки играют ключевую роль для такого счета.
     */
    CREDIT(2, "Кредитный");

    public static final int TYPES_COUNT = 3;

    private final int type;
    private final String displayName;

    private static final Map<Integer, AccountType> MAP = Arrays
            .stream(AccountType.values())
            .collect(Collectors.toMap(AccountType::getType, Function.identity()));

    public static AccountType of(int type) {
        return MAP.get(type);
    }

}
