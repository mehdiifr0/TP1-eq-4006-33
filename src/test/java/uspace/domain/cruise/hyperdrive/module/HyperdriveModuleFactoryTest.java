package uspace.domain.cruise.hyperdrive.module;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uspace.domain.cruise.dateTime.CruiseDateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HyperdriveModuleFactoryTest {
    private static final HyperdriveModuleId MODULE_ID = new HyperdriveModuleId("HY-77-V");

    private static final int POWER_LEVEL = 85;

    private static final int ACTIVATION_DAYS = 2;

    private static final LocalDate ACTIVATION_DATE = LocalDate.of(2085, 1, 26);

    private HyperdriveModuleFactory hyperdriveModuleFactory;

    @BeforeEach
    void createHyperdriveModuleFactory() {
        hyperdriveModuleFactory = new HyperdriveModuleFactory();
    }

    @Test
    void whenCreate_thenModuleHasGivenId() {
        HyperdriveModule hyperdriveModule = hyperdriveModuleFactory.create(MODULE_ID, POWER_LEVEL, ACTIVATION_DAYS,
                                                                           ACTIVATION_DATE);

        assertEquals(MODULE_ID, hyperdriveModule.getId());
    }

    @Test
    void whenCreate_thenModuleHasGivenPowerLevel() {
        HyperdriveModule hyperdriveModule = hyperdriveModuleFactory.create(MODULE_ID, POWER_LEVEL, ACTIVATION_DAYS,
                                                                           ACTIVATION_DATE);

        assertEquals(new HyperdrivePowerLevel(POWER_LEVEL), hyperdriveModule.getPowerLevel());
    }

    @Test
    void whenCreate_thenModuleIsActivatedAtOneInTheMorningOfActivationDate() {
        HyperdriveModule hyperdriveModule = hyperdriveModuleFactory.create(MODULE_ID, POWER_LEVEL, ACTIVATION_DAYS,
                                                                           ACTIVATION_DATE);

        CruiseDateTime expectedActivation = new CruiseDateTime(LocalDateTime.of(2085, 1, 26, 1, 0));
        assertEquals(expectedActivation, hyperdriveModule.getActivationDateTime());
    }

    @Test
    void whenCreate_thenModuleIsDeactivatedAtOneInTheMorningAfterActivationDays() {
        HyperdriveModule hyperdriveModule = hyperdriveModuleFactory.create(MODULE_ID, POWER_LEVEL, ACTIVATION_DAYS,
                                                                           ACTIVATION_DATE);

        CruiseDateTime expectedDeactivation = new CruiseDateTime(LocalDateTime.of(2085, 1, 28, 1, 0));
        assertEquals(expectedDeactivation, hyperdriveModule.getDeactivationDateTime());
    }
}
