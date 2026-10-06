package uspace.api.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.booking.traveler.exceptions.TravelerNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TravelerNotFoundExceptionMapperTest {
    private final TravelerNotFoundExceptionMapper mapper = new TravelerNotFoundExceptionMapper();

    @Test
    void whenToResponse_thenReturnNotFound() {
        Response response = mapper.toResponse(new TravelerNotFoundException());

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void whenToResponse_thenReturnTravelerNotFoundError() {
        Response response = mapper.toResponse(new TravelerNotFoundException());

        ErrorResponse errorResponse = (ErrorResponse) response.getEntity();
        assertEquals("TRAVELER_NOT_FOUND", errorResponse.error);
        assertEquals("Traveler not found", errorResponse.description);
    }
}