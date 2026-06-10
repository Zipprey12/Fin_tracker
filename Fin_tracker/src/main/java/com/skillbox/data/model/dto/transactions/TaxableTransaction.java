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

    private Float taxAmount;
    private BigDecimal amountWithoutTax;

    public TaxableTransaction() {
        super(TransactionType.TAXABLE);
    }

    public void setTaxAmount(float amount) {
        if (amount < 0 || amount > 100) {
            throw new IllegalArgumentException("Процентная ставка налога должна быть в диапазоне от 0 до 100");
        }
        taxAmount = amount;
        calculateAmount();
    }

    @Override
    public void setAmount(BigDecimal value){
        amountWithoutTax = value;
        calculateAmount();
    }

    @Override
    public BigDecimal calculateTax() {
        return amountWithoutTax
                .multiply(BigDecimal.valueOf(taxAmount))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private void calculateAmount(){
        if(taxAmount != null && amountWithoutTax != null){
            super.setAmount(amountWithoutTax.subtract(calculateTax()));
            return;
        }
        super.setAmount(null);
    }
}
