package com.skillbox.data.repository.mappers.transactions;

import com.skillbox.data.model.dto.transactions.RecurrentTransaction;
import com.skillbox.data.model.dto.transactions.Transaction;
import com.skillbox.data.model.enums.RecurrencePattern;
import com.skillbox.data.model.enums.TransactionType;
import com.skillbox.service.utils.StringUtil;

public class RecurrentTransactionPartsMapper extends TransactionPartsMapper{

    public static final int PERIOD_INDEX = 6;
    public static final int COUNT_INDEX = 7;

    @Override
    public int getMinPartsCount() {
        return 8;
    }

    @Override
    public Transaction initTransaction(TransactionType type, String[] parts) {
        var period = parts[PERIOD_INDEX];
        var count = StringUtil.parseToInt(parts[COUNT_INDEX]);
        var recurrence = parseRecurrence(period);

        if(count.isEmpty() || recurrence == null){
            return null;
        }

        RecurrentTransaction transaction = new RecurrentTransaction();
        transaction.setRecurrence(recurrence);
        transaction.setPaymentsCount(count.get());
        return transaction;
    }

    private RecurrencePattern parseRecurrence(String value){
        try {
            return RecurrencePattern.of(value);
        } catch (Exception e) {
            return null;
        }
    }
}
