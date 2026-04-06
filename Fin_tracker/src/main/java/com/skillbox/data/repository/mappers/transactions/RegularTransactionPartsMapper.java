package com.skillbox.data.repository.mappers.transactions;

import com.skillbox.data.model.dto.transactions.Transaction;
import com.skillbox.data.model.enums.TransactionType;

public class RegularTransactionPartsMapper extends TransactionPartsMapper{

    @Override
    public int getMinPartsCount() {
        return TransactionPartsMapper.GENERAL_PARTS_COUNT;
    }

    @Override
    public Transaction initTransaction(TransactionType type, String[] parts) {
        return new Transaction(TransactionType.REGULAR);
    }
}
