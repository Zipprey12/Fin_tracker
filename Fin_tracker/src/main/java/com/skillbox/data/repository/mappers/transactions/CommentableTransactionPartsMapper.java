package com.skillbox.data.repository.mappers.transactions;

import com.skillbox.data.model.dto.transactions.CommentableTransaction;
import com.skillbox.data.model.dto.transactions.Transaction;
import com.skillbox.data.model.enums.TransactionType;

public class CommentableTransactionPartsMapper extends TransactionPartsMapper{

    public static final int COMMENT_INDEX = 6;

    @Override
    public int getMinPartsCount() {
        return 7;
    }

    @Override
    public Transaction initTransaction(TransactionType type, String[] parts) {
        var comment = parts[COMMENT_INDEX];

        var transaction = new CommentableTransaction();
        transaction.setComment(comment);

        return transaction;
    }
}
