package uspace.api.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.hyperdrive.exceptions.InvalidHyperdriveActivationDateException;

@Provider
public class InvalidHyperdriveActivationDateExceptionMapper
        implements ExceptionMapper<InvalidHyperdriveActivationDateException> {

    @Override
    public Response toResponse(InvalidHyperdriveActivationDateException exception) {
        ErrorResponse error = new ErrorResponse("INVALID_ACTIVATION_DATE", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
