package com.skillbox.controller.option;

import lombok.Getter;

public enum GroupOption implements MenuOption {
    EXIT("вернуться назад (без группировки)"),
    BY_MONTHS("группировать по месяцам"),
    BY_YEARS("группировать по годам"),
    BY_DAY_OF_WEEK("группировать по дню недели"),
    BY_CATEGORY("группировать по категории"),
    EXPENSE_INCOME("считать доходы и расходы"),
    BY_ACCOUNT_TYPE("группировать по типу счёта"),
    BY_USER("Группировать по ID пользователя");

    @Getter
    private final String name;

    GroupOption(String name) {
        this.name = name;
    }

    @Override
    public int getOption() {
        return ordinal();
    }
}
