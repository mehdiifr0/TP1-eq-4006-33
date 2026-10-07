package uspace.api.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.hyperdrive.exceptions.InvalidHyperdriveActivationDateException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvalidHyperdriveActivationDateExceptionMapperTest {
    private final InvalidHyperdriveActivationDateExceptionMapper mapper = new InvalidHyperdriveActivationDateExceptionMapper();

    @Test
    void whenToResponse_thenReturnBadRequest() {
        Response response = mapper.toResponse(new InvalidHyperdriveActivationDateException());

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void whenToResponse_thenReturnInvalidActivationDateError() {
        Response response = mapper.toResponse(new InvalidHyperdriveActivationDateException());

        ErrorResponse errorResponse = (ErrorResponse) response.getEntity();
        assertEquals("INVALID_ACTIVATION_DATE", errorResponse.error);
        assertEquals("Activation date or deactivation date is outside the cruise timeframe", errorResponse.description);
    }
}
