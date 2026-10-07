package uspace.api.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.hyperdrive.exceptions.InvalidHyperdriveModuleIdFormatException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvalidHyperdriveModuleIdFormatExceptionMapperTest {
    private final InvalidHyperdriveModuleIdFormatExceptionMapper mapper = new InvalidHyperdriveModuleIdFormatExceptionMapper();

    @Test
    void whenToResponse_thenReturnBadRequest() {
        Response response = mapper.toResponse(new InvalidHyperdriveModuleIdFormatException());

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void whenToResponse_thenReturnInvalidIdFormatError() {
        Response response = mapper.toResponse(new InvalidHyperdriveModuleIdFormatException());

        ErrorResponse errorResponse = (ErrorResponse) response.getEntity();
        assertEquals("INVALID_HYPERDRIVE_ID_FORMAT", errorResponse.error);
        assertEquals("Invalid hyperdrive module id format", errorResponse.description);
    }
}
