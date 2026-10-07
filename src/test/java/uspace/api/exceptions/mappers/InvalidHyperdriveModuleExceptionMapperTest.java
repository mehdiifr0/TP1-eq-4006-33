package uspace.api.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.hyperdrive.exceptions.InvalidHyperdriveModuleException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvalidHyperdriveModuleExceptionMapperTest {
    private final InvalidHyperdriveModuleExceptionMapper mapper = new InvalidHyperdriveModuleExceptionMapper();

    @Test
    void whenToResponse_thenReturnBadRequest() {
        Response response = mapper.toResponse(new InvalidHyperdriveModuleException());

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void whenToResponse_thenReturnInvalidModuleError() {
        Response response = mapper.toResponse(new InvalidHyperdriveModuleException());

        ErrorResponse errorResponse = (ErrorResponse) response.getEntity();
        assertEquals("INVALID_HYPERDRIVE_MODULE", errorResponse.error);
        assertEquals("Hyperdrive module invalid", errorResponse.description);
    }
}
