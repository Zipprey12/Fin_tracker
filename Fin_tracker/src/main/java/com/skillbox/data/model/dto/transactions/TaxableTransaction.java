package com.skillbox.data.model.dto.transactions;

import com.skillbox.data.model.abstractions.Taxable;
import com.skillbox.data.model.enums.TransactionType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@EqualsAndHashCode(callSuper = true)
public class TaxableTransaction extends Transaction implements Taxable {

    private float taxAmount;

    public TaxableTransaction() {
        super(TransactionType.TAXABLE);
    }

    public void setTaxAmount(float amount) {
        if (amount < 0 || amount > 100) {
            throw new IllegalArgumentException("Процентная ставка налога должна быть в диапазоне от 0 до 100");
        }
        taxAmount = amount;
    }

    @Override
    public BigDecimal calculateTax() {
        return getAmount()
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(taxAmount));
    }
}
