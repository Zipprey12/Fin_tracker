package com.skillbox.data.repository.impl;

import com.skillbox.data.model.dto.Account;
import com.skillbox.data.repository.AbstractFileRepository;
import com.skillbox.data.repository.AccountRepository;
import com.skillbox.data.repository.FileReader;
import com.skillbox.data.repository.mappers.AccountLineMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class AccountRepositoryImpl extends AbstractFileRepository implements AccountRepository {

    public static final String FILE_READ_ERROR = "Ошибка при чтении файла со счетами";

    private final AccountLineMapper mapper = new AccountLineMapper();
    private final FileReader<Account> reader = new FileReader<>(mapper, FILE_READ_ERROR);

    public AccountRepositoryImpl(String fileName) {
        super(fileName);
    }

    @Override
    public List<Account> readAll() {
        return reader.readAll(getFileName());
    }

    @Override
    public Map<Integer, Account> readAllSortById() {
        Map<Integer, Account> result = new HashMap<>();
        var path = Path.of(getFileName());
        long index = 0;

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                index++;

                var account = mapper.mapToDto(line);
                if (account == null) {
                    log.warn("Строка {} содержит некорректные данные. Она была пропущена при чтении", index);
                    continue;
                }
                result.putIfAbsent(account.getAccountId(), account);
            }
        } catch (IOException e) {
            log.error(FILE_READ_ERROR, e);
        }
        return result;
    }
}
