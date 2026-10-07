package uspace.api.cruise.booking;

import org.junit.jupiter.api.Test;
import uspace.application.cruise.booking.dtos.newZeroGravityExperienceBooking.NewZeroGravityExperienceBookingDto;
import uspace.domain.exceptions.MissingParameterException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NewZeroGravityExperienceBookingDtoValidatorTest {
    private static final String ANY_DATE_TIME = "2084-04-08T12:30:00";

    private static final String MISSING_DATE_TIME_MESSAGE = "Missing parameter: experienceBookingDateTime";

    private final NewZeroGravityExperienceBookingDtoValidator validator =
            new NewZeroGravityExperienceBookingDtoValidator();

    @Test
    void givenMissingExperienceBookingDateTime_whenValidate_thenThrowMissingParameterException() {
        NewZeroGravityExperienceBookingDto dto = new NewZeroGravityExperienceBookingDto(null);

        assertThrows(MissingParameterException.class, () -> validator.validate(dto));
    }

    @Test
    void givenMissingExperienceBookingDateTime_whenValidate_thenExceptionNamesMissingParameter() {
        NewZeroGravityExperienceBookingDto dto = new NewZeroGravityExperienceBookingDto(null);

        MissingParameterException exception = assertThrows(MissingParameterException.class,
                                                           () -> validator.validate(dto));

        assertEquals(MISSING_DATE_TIME_MESSAGE, exception.getMessage());
    }

    @Test
    void givenNoRequestBody_whenValidate_thenThrowMissingParameterException() {
        assertThrows(MissingParameterException.class, () -> validator.validate(null));
    }

    @Test
    void givenExperienceBookingDateTime_whenValidate_thenDoNotThrow() {
        NewZeroGravityExperienceBookingDto dto = new NewZeroGravityExperienceBookingDto(ANY_DATE_TIME);

        assertDoesNotThrow(() -> validator.validate(dto));
    }
}
