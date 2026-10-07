package uspace.api.cruise;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uspace.api.cruise.hyperdrive.NewHyperdriveModuleDtoValidator;
import uspace.application.cruise.CruiseService;
import uspace.application.cruise.dtos.CruiseDto;
import uspace.application.cruise.hyperdrive.dtos.newHyperdriveModule.NewHyperdriveModuleDto;

@Path("/cruises")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CruiseResource {

    private final CruiseService cruiseService;
    private final NewHyperdriveModuleDtoValidator newHyperdriveModuleDtoValidator;

    @Inject
    public CruiseResource(CruiseService cruiseService, NewHyperdriveModuleDtoValidator newHyperdriveModuleDtoValidator) {
        this.cruiseService = cruiseService;
        this.newHyperdriveModuleDtoValidator = newHyperdriveModuleDtoValidator;
    }

    @GET
    @Path("{cruiseId}")
    public Response getCruise(@PathParam("cruiseId") String cruiseId) {
        CruiseDto cruiseDto = cruiseService.findCruise(cruiseId);
        return Response.ok(cruiseDto).build();
    }

    @POST
    @Path("{cruiseId}/hyperdrive-modules")
    public Response addHyperdriveModule(@PathParam("cruiseId") String cruiseId, NewHyperdriveModuleDto newHyperdriveModuleDto) {
        newHyperdriveModuleDtoValidator.validate(newHyperdriveModuleDto);

        cruiseService.addHyperdriveModule(cruiseId, newHyperdriveModuleDto);

        return Response.status(Response.Status.CREATED).build();
    }
}
