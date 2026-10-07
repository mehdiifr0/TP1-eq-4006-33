package uspace.domain.cruise.hyperdrive.module;

import org.junit.jupiter.api.Test;
import uspace.domain.cruise.hyperdrive.exceptions.InvalidHyperdriveModuleIdFormatException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HyperdriveModuleIdTest {

    @Test
    void givenValidId_whenCreate_thenIdIsKept() {
        HyperdriveModuleId hyperdriveModuleId = new HyperdriveModuleId("HY-321-Z");

        assertEquals("HY-321-Z", hyperdriveModuleId.toString());
    }

    @Test
    void givenSmallestNumber_whenCreate_thenDoesNotThrow() {
        assertDoesNotThrow(() -> new HyperdriveModuleId("HY-1-A"));
    }

    @Test
    void givenLargestNumber_whenCreate_thenDoesNotThrow() {
        assertDoesNotThrow(() -> new HyperdriveModuleId("HY-999-A"));
    }

    @Test
    void givenNumberZero_whenCreate_thenThrowInvalidHyperdriveModuleIdFormatException() {
        assertThrows(InvalidHyperdriveModuleIdFormatException.class, () -> new HyperdriveModuleId("HY-0-A"));
    }

    @Test
    void givenNumberOverLargest_whenCreate_thenThrowInvalidHyperdriveModuleIdFormatException() {
        assertThrows(InvalidHyperdriveModuleIdFormatException.class, () -> new HyperdriveModuleId("HY-1000-A"));
    }

    @Test
    void givenLowercaseLetter_whenCreate_thenThrowInvalidHyperdriveModuleIdFormatException() {
        assertThrows(InvalidHyperdriveModuleIdFormatException.class, () -> new HyperdriveModuleId("HY-321-z"));
    }

    @Test
    void givenLowercasePrefix_whenCreate_thenThrowInvalidHyperdriveModuleIdFormatException() {
        assertThrows(InvalidHyperdriveModuleIdFormatException.class, () -> new HyperdriveModuleId("hy-321-Z"));
    }

    @Test
    void givenTwoLetters_whenCreate_thenThrowInvalidHyperdriveModuleIdFormatException() {
        assertThrows(InvalidHyperdriveModuleIdFormatException.class, () -> new HyperdriveModuleId("HY-321-ZZ"));
    }

    @Test
    void givenNoLetter_whenCreate_thenThrowInvalidHyperdriveModuleIdFormatException() {
        assertThrows(InvalidHyperdriveModuleIdFormatException.class, () -> new HyperdriveModuleId("HY-321"));
    }

    @Test
    void givenNoId_whenCreate_thenThrowInvalidHyperdriveModuleIdFormatException() {
        assertThrows(InvalidHyperdriveModuleIdFormatException.class, () -> new HyperdriveModuleId(null));
    }
}
