package uspace.api.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.hyperdrive.exceptions.InvalidHyperdriveModuleIdFormatException;

@Provider
public class InvalidHyperdriveModuleIdFormatExceptionMapper
        implements ExceptionMapper<InvalidHyperdriveModuleIdFormatException> {

    @Override
    public Response toResponse(InvalidHyperdriveModuleIdFormatException exception) {
        ErrorResponse error = new ErrorResponse("INVALID_HYPERDRIVE_ID_FORMAT", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
