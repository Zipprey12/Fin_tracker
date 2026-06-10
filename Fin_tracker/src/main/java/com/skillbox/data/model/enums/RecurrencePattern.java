package com.skillbox.data.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Класс, представляющий паттерн повторения транзакций.
 */
@Getter
@RequiredArgsConstructor
public enum RecurrencePattern {
    HOURLY("hourly", ChronoUnit.HOURS, 1),
    DAILY("daily", ChronoUnit.DAYS, 1),
    WEEKLY("weekly", ChronoUnit.WEEKS, 1),
    BIWEEKLY("biweekly", ChronoUnit.WEEKS, 2),
    MONTHLY("monthly", ChronoUnit.MONTHS, 1),
    YEARLY("yearly", ChronoUnit.YEARS, 1);

    private final String pattern;
    private final ChronoUnit chronoUnit;
    private final int unitsCount;

    private static final Map<String, RecurrencePattern> MAP =
            Arrays.stream(RecurrencePattern.values())
                    .collect(Collectors.toMap(RecurrencePattern::getPattern, Function.identity()));

    public static RecurrencePattern of(String recurrencePattern) {
        return MAP.get(recurrencePattern);
    }
}
