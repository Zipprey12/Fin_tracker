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
import java.util.*;

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
    private TransactionFilterOptions filterOptions;

    private GroupType groupType;
    private AggregationType aggregationType;

    private Map<String, Object> statistic;

    public Map<String, Object> getFilters() {
        var result = new LinkedHashMap<String, Object>();

        if (filterOptions.getCategory() != null) {
            result.put("Category", filterOptions.getCategory());
        }

        if (filterOptions.getMinDate() != null) {
            result.put("Min date", DateUtil.formatForOut(filterOptions.getMinDate()));
        }
        if (filterOptions.getMaxDate() != null){
            result.put("Max date", DateUtil.formatForOut(filterOptions.getMaxDate()));
        }

        if(filterOptions.getComment() != null){
            result.put("Comment like", filterOptions.getComment());
        }

        if (filterOptions.getMaxAmount() != null){
            result.put("Max amount", filterOptions.getMaxAmount());
        }
        if(filterOptions.getMinAmount() != null){
            result.put("Min amount", filterOptions.getMinAmount());
        }
        return result;
    }

    public void print() {
        log.info("Group type : {}", groupType);
        log.info("Aggregation type: {}", aggregationType);

        for (var entry : statistic.entrySet()) {
            log.info("{} : {}", entry.getKey(), entry.getValue());
        }
    }
}
