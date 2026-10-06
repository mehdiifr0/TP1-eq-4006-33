package uspace.api.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceBookingTimeException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZeroGravityExperienceBookingTimeExceptionMapperTest {
    private final ZeroGravityExperienceBookingTimeExceptionMapper mapper = new ZeroGravityExperienceBookingTimeExceptionMapper();

    @Test
    void whenToResponse_thenReturnBadRequest() {
        Response response = mapper.toResponse(new ZeroGravityExperienceBookingTimeException());

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void whenToResponse_thenReturnBookingTimeError() {
        Response response = mapper.toResponse(new ZeroGravityExperienceBookingTimeException());

        ErrorResponse errorResponse = (ErrorResponse) response.getEntity();
        assertEquals("ZERO_GRAVITY_EXPERIENCE_BOOKING_TIME", errorResponse.error);
        assertEquals("Zero gravity experience booking time must be before the cruise departure time.", errorResponse.description);
    }
}