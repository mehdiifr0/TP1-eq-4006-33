package uspace.application.utils.dateParser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uspace.domain.exceptions.InvalidDateFormatException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LocalDateParserTest {
    private LocalDateParser localDateParser;

    @BeforeEach
    void createLocalDateParser() {
        localDateParser = new LocalDateParser();
    }

    @Test
    void givenDateWithYearMonthDayFormat_whenParse_thenReturnDate() {
        LocalDate date = localDateParser.parse("2085-01-26");

        assertEquals(LocalDate.of(2085, 1, 26), date);
    }

    @Test
    void givenDateWithDayMonthYearFormat_whenParse_thenThrowInvalidDateFormatException() {
        assertThrows(InvalidDateFormatException.class, () -> localDateParser.parse("26-01-2085"));
    }

    @Test
    void givenDateWithTime_whenParse_thenThrowInvalidDateFormatException() {
        assertThrows(InvalidDateFormatException.class, () -> localDateParser.parse("2085-01-26T01:00"));
    }

    @Test
    void givenDateThatDoesNotExist_whenParse_thenThrowInvalidDateFormatException() {
        assertThrows(InvalidDateFormatException.class, () -> localDateParser.parse("2085-02-30"));
    }
}
