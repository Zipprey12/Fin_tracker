package com.skillbox.service.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateUtil {

    public static final String FORMAT_FOR_DATE_TIME_OUT = "dd-MM-yyyy HH:mm:ss";
    public static final String FORMAT_FOR_DATE_OUT = "dd-MM-yyyy";
    public static final String FORMAT_FOR_FILE = "dd-MM-yyyy_HH-mm";

    public static final DateTimeFormatter FORMATTER_FOR_DATE_TIME_OUT = DateTimeFormatter.ofPattern(FORMAT_FOR_DATE_TIME_OUT);
    public static final DateTimeFormatter FORMATTER_FOR_DATE_OUT = DateTimeFormatter.ofPattern(FORMAT_FOR_DATE_OUT);
    public static final DateTimeFormatter FORMATTER_FOR_FILE = DateTimeFormatter.ofPattern(FORMAT_FOR_FILE);

    public static String formatForOut(LocalDateTime dateTime){
        return dateTime.format(FORMATTER_FOR_DATE_TIME_OUT);
    }

    public static String formatForOut(LocalDate dateTime){
        return dateTime.format(FORMATTER_FOR_DATE_OUT);
    }

    public static String formatForFileName(LocalDateTime dateTime){
        return dateTime.format(FORMATTER_FOR_FILE);
    }
}
