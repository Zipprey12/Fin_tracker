package com.skillbox.controller;

import com.skillbox.controller.dto.TransactionsAggregationDto;
import com.skillbox.controller.dto.TransactionsFilterDto;
import com.skillbox.controller.dto.TransactionsGroupingDto;
import com.skillbox.controller.option.MainMenuOption;
import com.skillbox.data.model.dto.Analytic;
import com.skillbox.data.repository.AnalyticRepository;
import com.skillbox.service.TransactionService;
import lombok.extern.slf4j.Slf4j;

/**
 * Консольный контроллер для управления навигацией по главному меню.
 */
@Slf4j
public class MainMenuController extends AbstractMenuController<MainMenuOption> {

    private final TransactionService transactionService;
    private final AnalyticRepository saver;

    private final FilterMenuController searchMenuController;
    private final GroupMenuController groupMenuController;
    private final AggregationMenuController aggregationMenuController;

    public MainMenuController(TransactionService transactionService, AnalyticRepository saver) {
        super(MainMenuOption.class, "Анализ финансов");
        this.transactionService = transactionService;
        this.saver = saver;
        this.groupMenuController = new GroupMenuController();
        this.aggregationMenuController = new AggregationMenuController();
        this.searchMenuController = new FilterMenuController();
    }

    public void start() {
        goMainMenu();
    }

    private void goMainMenu() {
        TransactionsFilterDto transactionFilter = new TransactionsFilterDto();
        TransactionsGroupingDto groupingDto = new TransactionsGroupingDto();
        TransactionsAggregationDto aggregateDto = new TransactionsAggregationDto();
        Analytic analytics = null;
        while (true) {
            MainMenuOption i = selectMenu();
            switch (i) {
                case SEARCH_CRITERIA:
                    transactionFilter = searchMenuController.createTransactionFilter();
                    break;
                case GROUP_OPTION:
                    groupingDto = groupMenuController.selectGrouping();
                    break;
                case AGGREGATION_METHOD:
                    aggregateDto = aggregationMenuController.selectAggregationFunction();
                    break;
                case CALCULATE_ANALYTICS:
                    analytics = transactionService.calculateAnalytics(transactionFilter,
                            groupingDto, aggregateDto);
                    analytics.print();
                    break;
                case SAVE_ANALYTICS:
                    if (analytics == null) {
                        log.error("Необходимо сначала рассчитать аналитику");
                        break;
                    }
                    saver.save(analytics);
                    break;
                case EXIT:
                    return;
            }
        }
    }
}
