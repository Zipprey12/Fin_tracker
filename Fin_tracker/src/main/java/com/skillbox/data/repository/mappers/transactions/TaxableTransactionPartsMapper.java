package com.skillbox.data.repository.mappers.transactions;

import com.skillbox.data.model.dto.transactions.TaxableTransaction;
import com.skillbox.data.model.dto.transactions.Transaction;
import com.skillbox.data.model.enums.TransactionType;
import com.skillbox.service.utils.StringUtil;

public class TaxableTransactionPartsMapper extends TransactionPartsMapper{

    public static final int TAX_AMOUNT_INDEX = 6;

    @Override
    public int getMinPartsCount() {
        return 7;
    }

    @Override
    public Transaction initTransaction(TransactionType type, String[] parts) {
        var amount = StringUtil.parseToBigDecimal(parts[AMOUNT_INDEX]);
        if(amount.isEmpty()){
            return null;
        }

        var taxAmount = StringUtil.parseToFloat(parts[TAX_AMOUNT_INDEX]);
        if(taxAmount.isEmpty()){
            return null;
        }

        var transaction = new TaxableTransaction();
        transaction.setAmount(amount.get());
        transaction.setTaxAmount(taxAmount.get());

        return transaction;
    }
}
