package uspace.application.utils.dateParser;

import uspace.domain.exceptions.InvalidDateFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateParser {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public LocalDate parse(String dateStr) {
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (Exception e) {
            throw new InvalidDateFormatException();
        }

        return date;
    }
}
