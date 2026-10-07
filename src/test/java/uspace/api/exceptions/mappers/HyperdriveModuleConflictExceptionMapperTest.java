package uspace.api.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.hyperdrive.exceptions.HyperdriveModuleConflictException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HyperdriveModuleConflictExceptionMapperTest {
    private final HyperdriveModuleConflictExceptionMapper mapper = new HyperdriveModuleConflictExceptionMapper();

    @Test
    void whenToResponse_thenReturnBadRequest() {
        Response response = mapper.toResponse(new HyperdriveModuleConflictException());

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void whenToResponse_thenReturnModuleConflictError() {
        Response response = mapper.toResponse(new HyperdriveModuleConflictException());

        ErrorResponse errorResponse = (ErrorResponse) response.getEntity();
        assertEquals("MODULE_CONFLICT", errorResponse.error);
        assertEquals("Another hyperdrive module is already active at this time", errorResponse.description);
    }
}
