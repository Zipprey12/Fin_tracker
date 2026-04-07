package com.skillbox.data.repository.mappers.transactions;

import com.skillbox.data.model.dto.transactions.ForeignCurrency;
import com.skillbox.data.model.dto.transactions.Transaction;
import com.skillbox.data.model.enums.TransactionType;
import com.skillbox.service.utils.StringUtil;

public class ForeignCurrencyPartsMapper extends TransactionPartsMapper{

    public static final int INTEREST_RATE_INDEX = 6;

    @Override
    public int getMinPartsCount() {
        return 7;
    }

    @Override
    public Transaction initTransaction(TransactionType type, String[] parts) {
        var interestRate = StringUtil.parseToFloat(parts[INTEREST_RATE_INDEX]);
        if(interestRate.isEmpty()) {
            return null;
        }

        var amount = StringUtil.parseToBigDecimal(parts[AMOUNT_INDEX]);
        if(amount.isEmpty()){
            return null;
        }

        var transaction =  new ForeignCurrency();
        transaction.setExchangeRate(interestRate.get());
        transaction.setAmount(amount.get());

        return transaction;
    }
}
