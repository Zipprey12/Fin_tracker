package com.skillbox.data.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionFilterOptions {

    private LocalDate minDate;
    private LocalDate maxDate;

    private String comment;

    private BigDecimal minAmount;
    private BigDecimal maxAmount;

    private String category;
}
