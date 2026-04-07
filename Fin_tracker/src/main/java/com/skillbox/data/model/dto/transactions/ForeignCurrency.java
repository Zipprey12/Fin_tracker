package com.skillbox.data.model.dto.transactions;

import com.skillbox.data.model.abstractions.CurrencyConvertible;
import com.skillbox.data.model.enums.TransactionType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@EqualsAndHashCode(callSuper = true)
public class ForeignCurrency extends Transaction implements CurrencyConvertible {

    private BigDecimal rawAmount;

    @Getter
    private Float exchangeRate;

    public ForeignCurrency() {
        super(TransactionType.FOREIGN_CURRENCY);
    }

    @Override
    public void setAmount(BigDecimal rowAmount){
        this.rawAmount = rowAmount;
        convert();
    }

    public void setExchangeRate(float value) {
        if (value < 0) {
            throw new IllegalArgumentException("Курс не может быть отрицательным числом");
        }
        this.exchangeRate = value;
        convert();
    }

    @Override
    public BigDecimal convertToBaseCurrency(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(exchangeRate))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private void convert(){
        if(rawAmount != null && exchangeRate != null){
            super.setAmount(convertToBaseCurrency(rawAmount));
            return;
        }
        super.setAmount(null);
    }
}
