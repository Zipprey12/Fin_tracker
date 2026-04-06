package com.skillbox.controller;

import com.skillbox.service.utils.StringUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
@UtilityClass
public class InputUtil {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String INCORRECT_INPUT = "Введено некорректное значение, оно не будет учитываться";

    public static String inputText(Scanner scanner, String message) {
        log.info(message + ":");
        var input = scanner.nextLine();
        return input.strip();
    }

    public static Optional<BigDecimal> inputDecimalValue(Scanner scanner, String message) {
        var inputText = inputText(scanner, message);
        Optional<BigDecimal> value = Optional.empty();

        if (!inputText.isEmpty()) {
            value = StringUtil.parseToBigDecimal(inputText);
            if (value.isEmpty()) {
                logIncorrectInput();
            }
        }
        return value;
    }

    public static Optional<LocalDateTime> inputDate(Scanner scanner, String message) {
        var inputText = inputText(scanner, message);

        if (!inputText.isEmpty()) {
            try {
                return Optional.of(LocalDateTime.parse(inputText, TIME_FORMATTER));
            } catch (Exception e) {
                logIncorrectInput();
            }
        }
        return Optional.empty();
    }

    private static void logIncorrectInput() {
        log.warn(INCORRECT_INPUT);
    }
}
