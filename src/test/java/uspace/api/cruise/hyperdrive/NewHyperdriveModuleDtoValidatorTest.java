package uspace.api.cruise.hyperdrive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uspace.application.cruise.hyperdrive.dtos.newHyperdriveModule.NewHyperdriveModuleDto;
import uspace.domain.exceptions.InvalidParameterException;
import uspace.domain.exceptions.MissingParameterException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NewHyperdriveModuleDtoValidatorTest {
    private static final String ANY_ID = "HY-77-V";
    private static final int ANY_POWER_LEVEL = 85;
    private static final int ANY_ACTIVATION_DAYS = 2;
    private static final String ANY_ACTIVATION_DATE = "2085-01-26";
    private static final int MISSING_NUMBER = 0;
    private static final int NEGATIVE_NUMBER = -1;

    private NewHyperdriveModuleDtoValidator validator;

    @BeforeEach
    void createValidator() {
        validator = new NewHyperdriveModuleDtoValidator();
    }

    @Test
    void givenAllParameters_whenValidate_thenDoesNotThrow() {
        NewHyperdriveModuleDto dto = new NewHyperdriveModuleDto(ANY_ID, ANY_POWER_LEVEL, ANY_ACTIVATION_DAYS, ANY_ACTIVATION_DATE);

        assertDoesNotThrow(() -> validator.validate(dto));
    }

    @Test
    void givenNoRequestBody_whenValidate_thenThrowMissingParameterException() {
        assertThrows(MissingParameterException.class, () -> validator.validate(null));
    }

    @Test
    void givenMissingId_whenValidate_thenExceptionNamesId() {
        NewHyperdriveModuleDto dto = new NewHyperdriveModuleDto(null, ANY_POWER_LEVEL, ANY_ACTIVATION_DAYS, ANY_ACTIVATION_DATE);

        MissingParameterException exception = assertThrows(MissingParameterException.class, () -> validator.validate(dto));

        assertEquals("Missing parameter: id", exception.getMessage());
    }

    @Test
    void givenMissingPowerLevel_whenValidate_thenExceptionNamesPowerLevel() {
        NewHyperdriveModuleDto dto = new NewHyperdriveModuleDto(ANY_ID, MISSING_NUMBER, ANY_ACTIVATION_DAYS, ANY_ACTIVATION_DATE);

        MissingParameterException exception = assertThrows(MissingParameterException.class, () -> validator.validate(dto));

        assertEquals("Missing parameter: powerLevel", exception.getMessage());
    }

    @Test
    void givenMissingActivationDays_whenValidate_thenExceptionNamesActivationDays() {
        NewHyperdriveModuleDto dto = new NewHyperdriveModuleDto(ANY_ID, ANY_POWER_LEVEL, MISSING_NUMBER, ANY_ACTIVATION_DATE);

        MissingParameterException exception = assertThrows(MissingParameterException.class, () -> validator.validate(dto));

        assertEquals("Missing parameter: activationDays", exception.getMessage());
    }

    @Test
    void givenMissingActivationDate_whenValidate_thenExceptionNamesActivationDate() {
        NewHyperdriveModuleDto dto = new NewHyperdriveModuleDto(ANY_ID, ANY_POWER_LEVEL, ANY_ACTIVATION_DAYS, null);

        MissingParameterException exception = assertThrows(MissingParameterException.class, () -> validator.validate(dto));

        assertEquals("Missing parameter: activationDate", exception.getMessage());
    }

    @Test
    void givenNegativePowerLevel_whenValidate_thenThrowInvalidParameterException() {
        NewHyperdriveModuleDto dto = new NewHyperdriveModuleDto(ANY_ID, NEGATIVE_NUMBER, ANY_ACTIVATION_DAYS, ANY_ACTIVATION_DATE);

        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> validator.validate(dto));

        assertEquals("Power level must be a positive number", exception.getMessage());
    }

    @Test
    void givenNegativeActivationDays_whenValidate_thenThrowInvalidParameterException() {
        NewHyperdriveModuleDto dto = new NewHyperdriveModuleDto(ANY_ID, ANY_POWER_LEVEL, NEGATIVE_NUMBER, ANY_ACTIVATION_DATE);

        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> validator.validate(dto));

        assertEquals("Activation days must be a positive number", exception.getMessage());
    }
}
