package com.skillbox.controller;

import com.skillbox.controller.dto.TransactionsAggregationDto;
import com.skillbox.controller.option.AggregateOption;
import com.skillbox.data.model.enums.AggregationType;

public class AggregationMenuController extends AbstractMenuController<AggregateOption> {
    private final TransactionsAggregationDto dto = new TransactionsAggregationDto();

    protected AggregationMenuController() {
        super(AggregateOption.class, "Выберите функцию агрегации транзакций");
    }

    public TransactionsAggregationDto selectAggregationFunction(){
        var selected = selectMenu();

        switch (selected){
            case AVG -> dto.setType(AggregationType.AVG);
            case COUNT -> dto.setType(AggregationType.COUNT);
            default ->  dto.setType(AggregationType.SUM);
        }

        return dto;
    }
}
