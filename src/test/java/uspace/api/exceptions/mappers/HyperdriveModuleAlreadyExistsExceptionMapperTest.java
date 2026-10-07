package uspace.api.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.hyperdrive.exceptions.HyperdriveModuleAlreadyExistsException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HyperdriveModuleAlreadyExistsExceptionMapperTest {
    private final HyperdriveModuleAlreadyExistsExceptionMapper mapper =
            new HyperdriveModuleAlreadyExistsExceptionMapper();

    @Test
    void whenToResponse_thenReturnBadRequest() {
        Response response = mapper.toResponse(new HyperdriveModuleAlreadyExistsException());

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void whenToResponse_thenReturnModuleAlreadyExistsError() {
        Response response = mapper.toResponse(new HyperdriveModuleAlreadyExistsException());

        ErrorResponse errorResponse = (ErrorResponse) response.getEntity();
        assertEquals("MODULE_ALREADY_EXISTS", errorResponse.error);
        assertEquals("Hyperdrive module already exists in the cruise", errorResponse.description);
    }
}
