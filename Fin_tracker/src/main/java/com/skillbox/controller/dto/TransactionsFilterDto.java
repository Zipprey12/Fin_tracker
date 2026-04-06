package com.skillbox.controller.dto;

import com.skillbox.data.model.abstractions.Recurring;
import com.skillbox.data.model.dto.TransactionFilterOptions;
import com.skillbox.data.model.dto.transactions.CommentableTransaction;
import com.skillbox.data.model.dto.transactions.Transaction;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * Класс для хранения фильтра по транзакциям.
 */
@Data
public class TransactionsFilterDto {

    private final TransactionFilterOptions options = new TransactionFilterOptions();

    /**
     * Собирает предикат для фильтрации транзакции.
     *
     * @return Предикат для фильтрации транзакции.
     */
    public Predicate<Transaction> buildPredicate() {
        return categoryPredicate()
                .and(amountPredicate())
                .and(commentPredicate())
                .and(datePredicate());
    }

    /**
     * Создает предикат для фильтрации транзакций по диапазону дат. Также вернет те Recurring транзакции, которые будут
     * или были выполнены в указанный диапазон дат
     *
     * @return Предикат для фильтрации транзакций по диапазону дат.
     */
    private Predicate<Transaction> datePredicate() {
        var minDate = options.getMinDate();
        var maxDate = options.getMaxDate();

        return transaction -> {
            LocalDateTime date = transaction.getDateTime();
            LocalDateTime start = minDate == null ? null : minDate.atStartOfDay();
            LocalDateTime end = maxDate == null ? null : maxDate.atStartOfDay();
            return (start == null || !date.isBefore(start))
                    && (end == null || !date.isAfter(end))
                    || (transaction instanceof Recurring recurring && recurring.isExecutedBetween(start, end));
        };
    }

    /**
     * Создает предикат для фильтрации транзакций по комментарию или его части. Фильтруются только транзакции,
     * имплементирующие интерфейс Commentable. Если токен пустой или null, то возвращается предикат, который всегда
     * вернет true
     *
     * @return Предикат для фильтрации транзакций по комментарию.
     */
    private Predicate<Transaction> commentPredicate() {
        var comment = options.getComment();

        return transaction -> (
                !(transaction instanceof CommentableTransaction commentable)
                        || comment == null
                        || commentable.getComment().toLowerCase()
                        .contains(comment.toLowerCase()));
    }

    /**
     * Создает предикат для фильтрации транзакций по диапазону суммы.
     *
     * @return Предикат для фильтрации транзакций по диапазону суммы.
     */
    private Predicate<Transaction> amountPredicate() {
        var minAmount = options.getMinAmount();
        var maxAmount = options.getMaxAmount();

        return transaction -> {
            var amount = transaction.getAmount();
            return (minAmount == null || amount.compareTo(minAmount) >= 0)
                    && (maxAmount == null || amount.compareTo(maxAmount) <= 0);
        };
    }

    /**
     * Создает предикат для фильтрации транзакций по категории.
     *
     * @return Предикат для фильтрации транзакций по категории.
     */
    private Predicate<Transaction> categoryPredicate() {
        var category = options.getCategory();

        return transaction -> {
            var c = transaction.getCategory();
            return category == null || c.equals(category);
        };
    }
}
