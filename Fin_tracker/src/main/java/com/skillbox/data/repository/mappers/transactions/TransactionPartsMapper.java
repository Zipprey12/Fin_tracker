package com.skillbox.data.repository.mappers.transactions;

import com.skillbox.data.model.dto.transactions.Transaction;
import com.skillbox.data.model.enums.TransactionType;
import com.skillbox.service.utils.StringUtil;

public abstract class TransactionPartsMapper {

    public static final int ACCOUNT_ID_INDEX = 0;
    public static final int ID_INDEX = 1;
    public static final int DATE_INDEX = 2;
    public static final int CATEGORY_INDEX = 3;
    public static final int AMOUNT_INDEX = 4;
    public static final int TYPE_INDEX = 5;
    public static final int GENERAL_PARTS_COUNT = 6;

    public abstract int getMinPartsCount();

    public abstract Transaction initTransaction(TransactionType type, String[] parts);

    public Transaction parseTransaction(String[] parts) {
        if (parts.length < getMinPartsCount()) {
            return null;
        }

        var type = parts[TYPE_INDEX];
        if (type.isEmpty()) {
            return null;
        }

        TransactionType parsed = parseType(type);
        if (parsed == null) {
            return null;
        }

        var transaction = initTransaction(parsed, parts);
        if (fillGeneralValues(parts, transaction)) {
            return transaction;
        }
        return null;
    }

    protected boolean fillGeneralValues(String[] parts, Transaction transaction) {
        var accountId = StringUtil.parseToInt(parts[ACCOUNT_ID_INDEX]);
        var id = StringUtil.parseToInt(parts[ID_INDEX]);
        var date = StringUtil.parseIsoDate(parts[DATE_INDEX]);
        var category = parts[CATEGORY_INDEX];
        var amount = StringUtil.parseToBigDecimal(parts[AMOUNT_INDEX]);

        if (accountId.isEmpty() || id.isEmpty() || date.isEmpty() ||
                category == null || amount.isEmpty()) {
            return false;
        }

        transaction.setAccountId(accountId.get());
        transaction.setTransactionId(id.get());
        transaction.setDateTime(date.get());
        transaction.setCategory(category);
        transaction.setAmount(amount.get());
        return true;
    }

    protected TransactionType parseType(String value) {
        return TransactionType.fromString(value).orElse(null);
    }
}
