package com.skillbox.data.repository;

import com.skillbox.data.repository.mappers.LineMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Slf4j
public class FileReader<T> {

    private final LineMapper<T> mapper;
    private final String readErrorMessage;

    public List<T> readAll(String fileName) {
        List<T> result = new LinkedList<>();
        var path = Path.of(fileName);
        long index = 0;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                index++;

                var account = mapper.mapToDto(line);
                if (account == null) {
                    log.warn("Строка {} содержит некорректные данные. Она была пропущена при чтении", index);
                    continue;
                }
                result.add(account);
            }
        } catch (IOException e) {
            log.error(readErrorMessage, e);
        }
        return result;
    }

}
