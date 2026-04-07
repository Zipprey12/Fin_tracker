package com.skillbox.data.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum TransactionType {

    REGULAR("Regular"),
    TAXABLE("Taxable"),
    RECURRENT("Recurrent"),
    FOREIGN_CURRENCY("ForeignCurrency"),
    COMMENTABLE("Commentable");

    @Getter
    private final String value;

    TransactionType(String value) {
        this.value = value;
    }

    private static final Map<String, TransactionType> MAP = Arrays
            .stream(TransactionType.values())
            .collect(Collectors.toMap(TransactionType::getValue, Function.identity()));

    public static Optional<TransactionType> fromString(String value){
        return Optional.ofNullable(MAP.get(value));
    }
}
