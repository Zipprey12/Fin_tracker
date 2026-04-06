package com.skillbox.controller.dto;

import com.skillbox.data.model.dto.transactions.Transaction;
import com.skillbox.data.model.enums.GroupType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransactionsGroupingDto {

    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter
            .ofPattern("MMMM yyyy", Locale.of("ru"));
    private static final DateTimeFormatter YEAR_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy", Locale.of("ru"));

    private static final Map<DayOfWeek, String> DAYS_OF_WEEK_NAMES = Map.of(
            DayOfWeek.MONDAY, "Понедельник",
            DayOfWeek.TUESDAY, "Вторник",
            DayOfWeek.WEDNESDAY, "Среда",
            DayOfWeek.THURSDAY, "Четверг",
            DayOfWeek.FRIDAY, "Пятница",
            DayOfWeek.SATURDAY, "Суббота",
            DayOfWeek.SUNDAY, "Воскресенье"
    );

    @Getter
    @Setter
    private GroupType type = GroupType.NONE;

    public Function<List<Transaction>, Map<String, List<Transaction>>> getGrouper() {
        return switch (type) {
            case BY_MONTH -> this::groupByMonths;
            case BY_YEAR -> this::groupByYear;
            case BY_WEEK_DAY -> this::groupByWeekDay;
            case BY_CATEGORY -> this::groupByCategory;
            case EXPENSE_INCOME -> this::groupExpenseIncome;
            case BY_ACCOUNT_TYPE -> this::groupByAccountType;
            case BY_USER -> this::groupByUser;
            default -> this::singleGroup;
        };
    }

    private Map<String, List<Transaction>> singleGroup(List<Transaction> transactions) {
        if (transactions == null) {
            return Collections.emptyMap();
        }
        return transactions.stream().collect(
                Collectors.toMap(
                        t -> String.valueOf(t.getTransactionId()),
                        Collections::singletonList,
                        (oldList, newList) -> {
                            List<Transaction> merged = new LinkedList<>(oldList);
                            merged.addAll(newList);
                            return merged;
                        },
                        LinkedHashMap::new
                )
        );
    }

    private Map<String, List<Transaction>> groupByMonths(List<Transaction> transactions) {
        return group(transactions, t -> t.getDateTime().format(MONTH_FORMATTER));
    }

    private Map<String, List<Transaction>> groupByYear(List<Transaction> transactions) {
        return group(transactions, t -> t.getDateTime().format(YEAR_FORMATTER));
    }

    private Map<String, List<Transaction>> groupByWeekDay(List<Transaction> transactions) {
        return group(transactions, t ->
                DAYS_OF_WEEK_NAMES.get(t.getDateTime().getDayOfWeek()));
    }

    private Map<String, List<Transaction>> groupByCategory(List<Transaction> transactions) {
        return group(transactions, t ->
                t.getCategory() == null || t.getCategory().isEmpty() ?
                        "Без категории" : t.getCategory());
    }

    private Map<String, List<Transaction>> groupExpenseIncome(List<Transaction> transactions) {
        return group(transactions, t ->
                t.getAmount().compareTo(BigDecimal.ZERO) >= 0 ? "Доходы" : "Расходы");
    }

    private Map<String, List<Transaction>> groupByUser(List<Transaction> transactions) {
        return group(transactions, t ->
                t.getAccount() == null ?
                        "Неизвестный" : String.valueOf(t.getAccount().getUserId()));
    }

    private Map<String, List<Transaction>> groupByAccountType(List<Transaction> transactions) {
        return group(transactions, t -> {
            var account = t.getAccount();
            return account == null ? "Неизвестный" :
                    account.getAccountType().getDisplayName();
        });
    }

    private Map<String, List<Transaction>> group(List<Transaction> transactions,
                                                 Function<Transaction, String> keyExtractor) {
        if (transactions == null) {
            return Collections.emptyMap();
        }
        return transactions.stream()
                .collect(Collectors.groupingBy(keyExtractor));
    }
}
