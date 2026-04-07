package com.skillbox.controller.option;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AggregateOption implements MenuOption {
    SUM("подсчет суммы"),
    AVG("подсчет среднего значения"),
    COUNT("подсчет количества");

    private final String name;

    @Override
    public int getOption() {
        return ordinal();
    }
}
