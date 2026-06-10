package com.skillbox.data.repository.impl;

import com.skillbox.data.model.dto.transactions.Transaction;
import com.skillbox.data.repository.AbstractFileRepository;
import com.skillbox.data.repository.FileReader;
import com.skillbox.data.repository.TransactionRepository;
import com.skillbox.data.repository.mappers.TransactionLineMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TransactionRepositoryImpl extends AbstractFileRepository implements TransactionRepository {

    public static final String FILE_READ_ERROR = "Ошибка при чтении файла с транзакциями";

    private final TransactionLineMapper mapper = new TransactionLineMapper();
    private final FileReader<Transaction> reader = new FileReader<>(mapper, FILE_READ_ERROR);

    public TransactionRepositoryImpl(String fileName) {
        super(fileName);
    }

    @Override
    public List<Transaction> readAll() {
        return reader.readAll(getFileName());
    }
}
