package uspace.api.cruise;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.api.cruise.hyperdrive.NewHyperdriveModuleDtoValidator;
import uspace.application.cruise.CruiseService;
import uspace.application.cruise.hyperdrive.dtos.newHyperdriveModule.NewHyperdriveModuleDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CruiseResourceTest {
    private static final String CRUISE_ID = "JUPITER_MOONS_EXPLORATION_2085";

    @Mock
    private CruiseService cruiseServiceMock;
    @Mock
    private NewHyperdriveModuleDtoValidator newHyperdriveModuleDtoValidatorMock;

    private CruiseResource cruiseResource;
    private NewHyperdriveModuleDto newHyperdriveModuleDto;

    @BeforeEach
    void createCruiseResource() {
        cruiseResource = new CruiseResource(cruiseServiceMock, newHyperdriveModuleDtoValidatorMock);
        newHyperdriveModuleDto = new NewHyperdriveModuleDto("HY-77-V", 85, 2, "2085-01-26");
    }

    @Test
    void whenAddHyperdriveModule_thenRequestIsValidated() {
        cruiseResource.addHyperdriveModule(CRUISE_ID, newHyperdriveModuleDto);

        verify(newHyperdriveModuleDtoValidatorMock).validate(newHyperdriveModuleDto);
    }

    @Test
    void whenAddHyperdriveModule_thenServiceAddsHyperdriveModule() {
        cruiseResource.addHyperdriveModule(CRUISE_ID, newHyperdriveModuleDto);

        verify(cruiseServiceMock).addHyperdriveModule(CRUISE_ID, newHyperdriveModuleDto);
    }

    @Test
    void whenAddHyperdriveModule_thenReturnCreated() {
        Response response = cruiseResource.addHyperdriveModule(CRUISE_ID, newHyperdriveModuleDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }
}
