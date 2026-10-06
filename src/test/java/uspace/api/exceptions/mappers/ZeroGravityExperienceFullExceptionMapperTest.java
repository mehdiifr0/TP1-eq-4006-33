package uspace.api.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceFullException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZeroGravityExperienceFullExceptionMapperTest {
    private final ZeroGravityExperienceFullExceptionMapper mapper = new ZeroGravityExperienceFullExceptionMapper();

    @Test
    void whenToResponse_thenReturnBadRequest() {
        Response response = mapper.toResponse(new ZeroGravityExperienceFullException());

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void whenToResponse_thenReturnExperienceFullError() {
        Response response = mapper.toResponse(new ZeroGravityExperienceFullException());

        ErrorResponse errorResponse = (ErrorResponse) response.getEntity();
        assertEquals("ZERO_GRAVITY_EXPERIENCE_FULL", errorResponse.error);
        assertEquals("Zero gravity experience is full", errorResponse.description);
    }
}