package com.skillbox.controller.dto;

import com.skillbox.data.model.dto.transactions.Transaction;
import com.skillbox.data.model.enums.AggregationType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TransactionsAggregationDto {

    @Getter
    @Setter
    private AggregationType type = AggregationType.SUM;

    public Function<Map<String, List<Transaction>>, Map<String, Object>> getGroupAggregationFunction() {
        return switch (type) {
            case AVG -> this::avg;
            case COUNT -> this::count;
            default -> this::sum;
        };
    }

    public Map<String, Object> sum(Map<String, List<Transaction>> map) {
        return aggregate(map, transactions ->
                transactions.stream()
                        .map(Transaction::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public Map<String, Object> count(Map<String, List<Transaction>> map) {
        return aggregate(map, List::size);
    }

    public Map<String, Object> avg(Map<String, List<Transaction>> map) {
        return aggregate(map, transactions ->
        {
            if (transactions == null || transactions.isEmpty()) {
                return BigDecimal.ZERO;
            }
            return transactions.stream()
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    .divide(BigDecimal.valueOf(transactions.size()), 2, RoundingMode.HALF_UP);
        });

    }

    private Map<String, Object> aggregate(Map<String, List<Transaction>> map,
                                          Function<List<Transaction>, Object> function) {
        if (map == null || map.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Object> result = new LinkedHashMap<>();

        for (var entry : map.entrySet()) {
            result.put(entry.getKey(), function.apply(entry.getValue()));
        }
        return result;
    }
}
