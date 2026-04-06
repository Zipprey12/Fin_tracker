package com.skillbox.data.model.dto.transactions;

import com.skillbox.data.model.abstractions.CurrencyConvertible;
import com.skillbox.data.model.enums.TransactionType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ForeignCurrency extends Transaction implements CurrencyConvertible {

    private float interestRate;

    public ForeignCurrency() {
        super(TransactionType.FOREIGN_CURRENCY);
    }

    public void setInterestRate(float value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("Процентная ставка должна быть от 0 до 100%");
        }
        this.interestRate = value;
    }

    @Override
    public BigDecimal convertToBaseCurrency(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(100 - interestRate))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
    }
}
