package com.skillbox.service;

import com.skillbox.controller.dto.TransactionsAggregationDto;
import com.skillbox.controller.dto.TransactionsFilterDto;
import com.skillbox.controller.dto.TransactionsGroupingDto;
import com.skillbox.data.model.dto.Analytic;

/**
 * Интерфейс для обработки транзакций и расчета аналитических данных.
 */
public interface TransactionService {

    /**
     * Вычисляет аналитические данные для заданных транзакций.
     *
     * @param transactionFilter объект фильтрации транзакций, содержащий параметры фильтра.
     * @param groupOption       опция для группировки транзакций.
     * @param aggregateOption   опция для агрегирования данных.
     * @return объект {@link Analytic}, содержащий результаты вычислений.
     * @throws IllegalArgumentException если любой из параметров равен null.
     */
    Analytic calculateAnalytics(TransactionsFilterDto transactionFilter,
                                TransactionsGroupingDto groupOption,
                                TransactionsAggregationDto aggregateOption);
}
