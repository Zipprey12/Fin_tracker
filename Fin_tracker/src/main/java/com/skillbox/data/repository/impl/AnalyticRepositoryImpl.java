package com.skillbox.data.repository.impl;

import com.skillbox.data.model.dto.Analytic;
import com.skillbox.data.repository.AbstractFileRepository;
import com.skillbox.data.repository.AnalyticRepository;
import com.skillbox.service.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Slf4j
public class AnalyticRepositoryImpl extends AbstractFileRepository implements AnalyticRepository {

    private final ObjectMapper objectMapper;

    public AnalyticRepositoryImpl(String fileName) {
        super(fileName);

        objectMapper = JsonMapper.builder()
                .disable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();
    }

    @Override
    public void save(Analytic analytic) {
        var name = createFileName();

        try {
            Path path = Paths.get(name);
            Path parent = path.getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            objectMapper.writeValue(path.toFile(), analytic);
            log.info("Статистика сохранена в: {}", path.toAbsolutePath());
        } catch (Exception e) {
            log.error("Ошибка сохранения статистики", e);
        }
    }

    private String createFileName() {
        return getFileName() + "/analytic_" + DateUtil.formatForFileName(LocalDateTime.now())
                + ".json";
    }
}
