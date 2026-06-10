package com.skillbox.data.repository.mappers;

import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class LineSplitter {

    private static final char SPLIT_SYMBOL = ',';

    public String[] split(String text) {
        StringBuilder current = new StringBuilder();
        List<String> parts = new LinkedList<>();

        boolean inQuotes = false;
        boolean escapeNext = false;

        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);

            if (escapeNext) {
                current.append(symbol);
                escapeNext = false;
                continue;
            }

            switch (symbol) {
                case SPLIT_SYMBOL:
                    savePart(current, parts);
                    break;

                case '\\':
                    escapeNext = true;
                    break;

                case '"':
                    if (inQuotes) {
                        savePart(current, parts);
                    }
                    inQuotes = !inQuotes;
                    break;

                case ' ':
                    if (inQuotes) {
                        current.append(symbol);
                    } else {
                        savePart(current, parts);
                    }
                    break;

                default:
                    current.append(symbol);
                    break;
            }
        }

        savePart(current, parts);
        return parts.toArray(parts.toArray(new String[0]));
    }

    private void savePart(StringBuilder currentToken, List<String> parts) {
        var value = currentToken.toString();
        parts.add(value);
        currentToken.setLength(0);
    }
}
