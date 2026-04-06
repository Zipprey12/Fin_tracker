package com.skillbox.data.repository.mappers;

import com.skillbox.data.model.dto.transactions.Transaction;
import com.skillbox.data.repository.mappers.transactions.*;

import java.util.Map;

public class TransactionLineMapper implements LineMapper<Transaction>{

    private final LineSplitter splitter = new LineSplitter();

    private final Map<String, TransactionPartsMapper> mappers = Map.of(
            "Regular", new RegularTransactionPartsMapper(),
            "Taxable", new TaxableTransactionPartsMapper(),
            "Recurrent", new RecurrentTransactionPartsMapper(),
            "ForeignCurrency", new ForeignCurrencyPartsMapper(),
            "Commentable", new CommentableTransactionPartsMapper()
    );

    @Override
    public Transaction mapToDto(String value) {
        String[] parts = splitter.split(value);

        if (parts.length < TransactionPartsMapper.GENERAL_PARTS_COUNT) {
            return null;
        }

        var type = parts[TransactionPartsMapper.TYPE_INDEX];
        var mapper = mappers.get(type);

        if (mapper == null) {
            return null;
        }
        return mapper.parseTransaction(parts);
    }
}
