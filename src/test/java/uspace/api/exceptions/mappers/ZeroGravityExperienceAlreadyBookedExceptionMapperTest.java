package uspace.api.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceAlreadyBookedException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZeroGravityExperienceAlreadyBookedExceptionMapperTest {
    private final ZeroGravityExperienceAlreadyBookedExceptionMapper mapper =
            new ZeroGravityExperienceAlreadyBookedExceptionMapper();

    @Test
    void whenToResponse_thenReturnBadRequest() {
        Response response = mapper.toResponse(new ZeroGravityExperienceAlreadyBookedException());

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void whenToResponse_thenReturnAlreadyBookedError() {
        Response response = mapper.toResponse(new ZeroGravityExperienceAlreadyBookedException());

        ErrorResponse errorResponse = (ErrorResponse) response.getEntity();
        assertEquals("ZERO_GRAVITY_EXPERIENCE_ALREADY_BOOKED", errorResponse.error);
        assertEquals("Zero gravity experience already booked by traveler", errorResponse.description);
    }
}
