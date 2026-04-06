package com.skillbox.service;

import com.skillbox.controller.dto.TransactionsAggregationDto;
import com.skillbox.controller.dto.TransactionsFilterDto;
import com.skillbox.controller.dto.TransactionsGroupingDto;
import com.skillbox.data.model.dto.Account;
import com.skillbox.data.model.dto.Analytic;
import com.skillbox.data.model.dto.TransactionFilterOptions;
import com.skillbox.data.model.dto.transactions.Transaction;
import com.skillbox.data.model.enums.AggregationType;
import com.skillbox.data.model.enums.GroupType;
import com.skillbox.data.repository.AccountRepository;
import com.skillbox.data.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    private List<Transaction> transactions;
    private Map<Integer, Account> accounts;
    private boolean isConnected = false;

    @Override
    public Analytic calculateAnalytics(TransactionsFilterDto transactionFilter,
                                       TransactionsGroupingDto groupingDto,
                                       TransactionsAggregationDto aggregationDto) {
        if (transactions == null) {
            update();
        }

        var filtered = transactions.stream()
                .filter(transactionFilter.buildPredicate())
                .toList();

        if (isConnectionNeeded(groupingDto.getType()) && !connectData()) {
            log.error("Не удалось соединить данные транзакций с аккаунтами, аналитика не была посчитана");
            return null;
        }

        var grouper = groupingDto.getGrouper();
        Map<String, List<Transaction>> grouped = grouper.apply(filtered);

        var aggregator = aggregationDto.getGroupAggregationFunction();
        Map<String, Object> aggregated = aggregator.apply(grouped);


        return createAnalytic(aggregated, groupingDto.getType(), aggregationDto.getType(), transactionFilter.getOptions());
    }

    public void update() {
        isConnected = false;
        transactions = transactionRepository.readAll();
        accounts = null;
    }

    public boolean isConnectionNeeded(GroupType type) {
        return !isConnected && (type == GroupType.BY_ACCOUNT_TYPE || type == GroupType.BY_USER);
    }

    private Analytic createAnalytic(Map<String, Object> aggregatedData,
                                    GroupType groupType, AggregationType aggregationType, TransactionFilterOptions options) {
        return Analytic.builder()
                .createdDateTime(LocalDateTime.now())
                .groupType(groupType)
                .aggregationType(aggregationType)
                .statistic(aggregatedData)
                .filterOptions(options)
                .build();
    }

    private boolean connectData() {
        if (accounts == null) {
            accounts = accountRepository.readAllSortById();
        }
        if (accounts == null || transactions == null) {
            return false;
        }
        for (var transaction : transactions) {
            var accountId = transaction.getAccountId();
            var account = accounts.get(accountId);
            if (account != null) {
                transaction.setAccount(account);
            }
        }
        isConnected = true;
        return true;
    }
}
