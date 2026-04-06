package com.skillbox.data.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skillbox.data.model.enums.AggregationType;
import com.skillbox.data.model.enums.GroupType;
import com.skillbox.service.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Класс, хранящий результаты расчета аналитики транзакций
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
@Slf4j
public class Analytic {

    @JsonFormat(pattern = DateUtil.FORMAT_FOR_DATE_TIME_OUT)
    private LocalDateTime createdDateTime;

    @JsonIgnore
    private TransactionFilterOptions filterOptions = new TransactionFilterOptions();

    private GroupType groupType;
    private AggregationType aggregationType;

    private Map<String, Object> statistic;

    public List<String> getFilters() {
        List<String> list = new LinkedList<>();

        if (filterOptions.getCategory() != null) {
            list.add("Category: " + filterOptions.getCategory());
        }

        if (filterOptions.getMinDate() != null) {
            list.add("Min date: " + DateUtil.formatForOut(filterOptions.getMinDate()));
        }
        if (filterOptions.getMaxDate() != null){
            list.add("Max date: " + DateUtil.formatForOut(filterOptions.getMaxDate()));
        }

        if(filterOptions.getComment() != null){
            list.add("Comment like \"" + filterOptions.getComment() + "\"");
        }

        if (filterOptions.getMaxAmount() != null){
            list.add("Max amount: " + filterOptions.getMaxAmount());
        }
        if(filterOptions.getMinAmount() != null){
            list.add("Min amount: " + filterOptions.getMinAmount());
        }
        return list;
    }

    public void print() {
        log.info("Group type : {}", groupType);
        log.info("Aggregation type: {}", aggregationType);

        for (var entry : statistic.entrySet()) {
            log.info("{} : {}", entry.getKey(), entry.getValue());
        }
    }
}
