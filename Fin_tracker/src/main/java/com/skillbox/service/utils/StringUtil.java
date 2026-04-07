package com.skillbox.service.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@UtilityClass
public class StringUtil {

    public static Optional<Integer> parseToInt(String value){
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Float> parseToFloat(String value){
        try {
            return Optional.of(Float.parseFloat(value));
        } catch (Exception e){
            return Optional.empty();
        }
    }

    public static Optional<Double> parseToDouble(String value){
        try {
            return Optional.of(Double.parseDouble(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<BigDecimal> parseToBigDecimal(String value){
        try {
            return Optional.of(new BigDecimal(value));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Optional<LocalDateTime> parseIsoDate(String value){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            return Optional.of(LocalDateTime.parse(value, formatter));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
