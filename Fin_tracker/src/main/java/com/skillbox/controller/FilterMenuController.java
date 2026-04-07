package com.skillbox.controller;

import com.skillbox.controller.dto.TransactionsFilterDto;
import com.skillbox.controller.option.SearchOption;
import com.skillbox.data.model.dto.TransactionFilterOptions;
import lombok.extern.slf4j.Slf4j;

import static com.skillbox.controller.InputUtil.*;

/**
 * Консольный контроллер для управления навигацией по функционалу поиска транзакций.
 */
@Slf4j
public class FilterMenuController extends AbstractMenuController<SearchOption> {

    public static final String DATE_DESCRIPTION =
            "Будут найдены транзакции, которые находятся в диапазоне дат, " +
            "а также повторяющиеся транзакции, которые выполнятся в указанном диапазоне. \n" +
            "Формат даты: ГГГГ-ММ-ДД, например 2024-09-27.";


    public static final String ENTER_MIN_VALUE = "Введите минимальное значение";
    public static final String ENTER_MAX_VALUE = "Введите максимальное значение";
    public static final String ENTER_START_DATE = "Введите начальную дату";
    public static final String ENTER_END_DATE = "Введите конечную дату";

    public FilterMenuController() {
        super(SearchOption.class, "Выберите способ поиска транзакции");
    }

    public TransactionsFilterDto createTransactionFilter() {
        TransactionsFilterDto filter = new TransactionsFilterDto();
        var options = filter.getOptions();

        while (true) {
            SearchOption option = selectMenu();
            switch (option) {
                case EXIT:
                    return filter;
                case ALL_TRANSACTION:
                    return new TransactionsFilterDto();
                case SEARCH_BY_CATEGORY:
                    inputCategory(options);
                    break;
                case SEARCH_BY_DATES:
                    inputDates(options);
                    break;
                case SEARCH_BY_AMOUNT:
                    inputAmount(options);
                    break;
                case SEARCH_BY_COMMENT:
                    inputComment(options);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + option);
            }
        }
    }

    private void inputComment(TransactionFilterOptions options) {
        var input = inputText(scanner, "Введите текст комментария");
        options.setComment(input);
    }

    private void inputAmount(TransactionFilterOptions options) {
        var minValue = inputDecimalValue(scanner, ENTER_MIN_VALUE);
        var maxValue = inputDecimalValue(scanner, ENTER_MAX_VALUE);

        var isMinPresent = minValue.isPresent();
        var isMaxPresent = maxValue.isPresent();

        if (isMinPresent && isMaxPresent
                && minValue.get().compareTo(maxValue.get()) > 0) {
            log.warn("Минимальное значение не может быть больше максимального");
            return;
        }

        options.setMinAmount(isMinPresent ? minValue.get() : null);
        options.setMaxAmount(isMaxPresent ? maxValue.get() : null);
    }

    private void inputDates(TransactionFilterOptions options) {
        log.info(DATE_DESCRIPTION);

        var minDate = inputDate(scanner, ENTER_START_DATE);
        var maxDate = inputDate(scanner, ENTER_END_DATE);

        var isMinPresent = minDate.isPresent();
        var isMaxPresent = maxDate.isPresent();

        if (isMinPresent && isMaxPresent
                && minDate.get().isAfter(maxDate.get())) {
            log.warn("Минимальная дата не может быть позже максимальной");
            return;
        }

        options.setMinDate(isMinPresent ? minDate.get().toLocalDate() : null);
        options.setMaxDate(isMaxPresent ? maxDate.get().toLocalDate() : null);
    }

    private void inputCategory(TransactionFilterOptions options) {
        var category = inputText(scanner, "Введите название категории (с учетом регистра)");
        options.setCategory(category);
    }
}
