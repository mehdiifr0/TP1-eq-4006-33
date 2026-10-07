package uspace.application.cruise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.application.cruise.hyperdrive.dtos.newHyperdriveModule.NewHyperdriveModuleDto;
import uspace.application.utils.dateParser.LocalDateParser;
import uspace.domain.cruise.Cruise;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.CruiseRepository;
import uspace.domain.cruise.exceptions.CruiseNotFoundException;
import uspace.domain.cruise.hyperdrive.HyperdriveStabilityValidator;
import uspace.domain.cruise.hyperdrive.exceptions.InvalidHyperdriveModuleException;
import uspace.domain.cruise.hyperdrive.exceptions.InvalidHyperdriveModuleIdFormatException;
import uspace.domain.cruise.hyperdrive.module.HyperdriveModule;
import uspace.domain.cruise.hyperdrive.module.HyperdriveModuleFactory;
import uspace.domain.cruise.hyperdrive.module.HyperdriveModuleId;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CruiseServiceTest {
    private static final String CRUISE_ID = "JUPITER_MOONS_EXPLORATION_2085";

    private static final String MODULE_ID = "HY-77-V";

    private static final int POWER_LEVEL = 85;

    private static final int ACTIVATION_DAYS = 2;

    private static final String ACTIVATION_DATE_STR = "2085-01-26";

    private static final LocalDate ACTIVATION_DATE = LocalDate.of(2085, 1, 26);

    @Mock
    private CruiseRepository cruiseRepositoryMock;

    @Mock
    private CruiseAssembler cruiseAssemblerMock;

    @Mock
    private HyperdriveModuleFactory hyperdriveModuleFactoryMock;

    @Mock
    private HyperdriveStabilityValidator hyperdriveStabilityValidatorMock;

    @Mock
    private LocalDateParser localDateParserMock;

    @Mock
    private Cruise cruiseMock;

    @Mock
    private HyperdriveModule hyperdriveModuleMock;

    private CruiseService cruiseService;

    private NewHyperdriveModuleDto newHyperdriveModuleDto;

    @BeforeEach
    void createCruiseService() {
        cruiseService = new CruiseService(cruiseRepositoryMock, cruiseAssemblerMock, hyperdriveModuleFactoryMock,
                                          hyperdriveStabilityValidatorMock, localDateParserMock);
        newHyperdriveModuleDto = new NewHyperdriveModuleDto(MODULE_ID, POWER_LEVEL, ACTIVATION_DAYS,
                                                            ACTIVATION_DATE_STR);
    }

    @Test
    void givenUnknownCruise_whenAddHyperdriveModule_thenThrowCruiseNotFoundException() {
        when(cruiseRepositoryMock.findById(new CruiseId(CRUISE_ID))).thenReturn(null);

        assertThrows(CruiseNotFoundException.class,
                     () -> cruiseService.addHyperdriveModule(CRUISE_ID, newHyperdriveModuleDto));
    }

    @Test
    void givenModuleIdWithInvalidFormat_whenAddHyperdriveModule_thenThrowInvalidHyperdriveModuleIdFormatException() {
        when(cruiseRepositoryMock.findById(new CruiseId(CRUISE_ID))).thenReturn(cruiseMock);
        newHyperdriveModuleDto.id = "HY-0-V";

        assertThrows(InvalidHyperdriveModuleIdFormatException.class,
                     () -> cruiseService.addHyperdriveModule(CRUISE_ID, newHyperdriveModuleDto));
    }

    @Test
    void givenUnstableModule_whenAddHyperdriveModule_thenThrowInvalidHyperdriveModuleException() {
        givenExistingCruiseAndValidDate();
        when(hyperdriveStabilityValidatorMock.isStable(new HyperdriveModuleId(MODULE_ID))).thenReturn(false);

        assertThrows(InvalidHyperdriveModuleException.class,
                     () -> cruiseService.addHyperdriveModule(CRUISE_ID, newHyperdriveModuleDto));
    }

    @Test
    void givenUnstableModule_whenAddHyperdriveModule_thenModuleIsNotAddedToCruise() {
        givenExistingCruiseAndValidDate();
        when(hyperdriveStabilityValidatorMock.isStable(new HyperdriveModuleId(MODULE_ID))).thenReturn(false);

        assertThrows(InvalidHyperdriveModuleException.class,
                     () -> cruiseService.addHyperdriveModule(CRUISE_ID, newHyperdriveModuleDto));

        verify(cruiseMock, never()).addHyperdriveModule(any());
    }

    @Test
    void givenStableModule_whenAddHyperdriveModule_thenModuleIsAddedToCruise() {
        givenExistingCruiseAndValidDate();
        givenStableModuleCreatedByFactory();

        cruiseService.addHyperdriveModule(CRUISE_ID, newHyperdriveModuleDto);

        verify(cruiseMock).addHyperdriveModule(hyperdriveModuleMock);
    }

    @Test
    void givenStableModule_whenAddHyperdriveModule_thenCruiseIsSaved() {
        givenExistingCruiseAndValidDate();
        givenStableModuleCreatedByFactory();

        cruiseService.addHyperdriveModule(CRUISE_ID, newHyperdriveModuleDto);

        verify(cruiseRepositoryMock).save(cruiseMock);
    }

    private void givenExistingCruiseAndValidDate() {
        when(cruiseRepositoryMock.findById(new CruiseId(CRUISE_ID))).thenReturn(cruiseMock);
        when(localDateParserMock.parse(ACTIVATION_DATE_STR)).thenReturn(ACTIVATION_DATE);
    }

    private void givenStableModuleCreatedByFactory() {
        HyperdriveModuleId hyperdriveModuleId = new HyperdriveModuleId(MODULE_ID);
        when(hyperdriveStabilityValidatorMock.isStable(hyperdriveModuleId)).thenReturn(true);
        when(hyperdriveModuleFactoryMock.create(hyperdriveModuleId, POWER_LEVEL, ACTIVATION_DAYS, ACTIVATION_DATE))
                .thenReturn(hyperdriveModuleMock);
    }
}
