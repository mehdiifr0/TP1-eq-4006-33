package uspace.domain.cruise.hyperdrive.module;

import org.junit.jupiter.api.Test;
import uspace.domain.cruise.dateTime.CruiseDateTime;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HyperdriveModuleTest {
    private static final HyperdrivePowerLevel ANY_POWER_LEVEL = new HyperdrivePowerLevel(85);

    private static final CruiseDateTime JANUARY_26 = dateTime(26);

    private static final CruiseDateTime JANUARY_27 = dateTime(27);

    private static final CruiseDateTime JANUARY_28 = dateTime(28);

    private static final CruiseDateTime JANUARY_29 = dateTime(29);

    private static final CruiseDateTime JANUARY_30 = dateTime(30);

    @Test
    void givenModuleActiveInsidePeriod_whenIsActiveBetween_thenReturnTrue() {
        HyperdriveModule hyperdriveModule = createModule("HY-1-A", JANUARY_27, JANUARY_28);

        assertTrue(hyperdriveModule.isActiveBetween(JANUARY_26, JANUARY_29));
    }

    @Test
    void givenActivationAtStartOfPeriod_whenIsActiveBetween_thenReturnFalse() {
        HyperdriveModule hyperdriveModule = createModule("HY-1-A", JANUARY_26, JANUARY_28);

        assertFalse(hyperdriveModule.isActiveBetween(JANUARY_26, JANUARY_29));
    }

    @Test
    void givenActivationBeforeStartOfPeriod_whenIsActiveBetween_thenReturnFalse() {
        HyperdriveModule hyperdriveModule = createModule("HY-1-A", JANUARY_26, JANUARY_28);

        assertFalse(hyperdriveModule.isActiveBetween(JANUARY_27, JANUARY_29));
    }

    @Test
    void givenDeactivationAtEndOfPeriod_whenIsActiveBetween_thenReturnFalse() {
        HyperdriveModule hyperdriveModule = createModule("HY-1-A", JANUARY_27, JANUARY_29);

        assertFalse(hyperdriveModule.isActiveBetween(JANUARY_26, JANUARY_29));
    }

    @Test
    void givenDeactivationAfterEndOfPeriod_whenIsActiveBetween_thenReturnFalse() {
        HyperdriveModule hyperdriveModule = createModule("HY-1-A", JANUARY_27, JANUARY_30);

        assertFalse(hyperdriveModule.isActiveBetween(JANUARY_26, JANUARY_29));
    }

    @Test
    void givenOtherModuleActivatedDuringModule_whenIsActiveAtSameTimeAs_thenReturnTrue() {
        HyperdriveModule hyperdriveModule = createModule("HY-1-A", JANUARY_26, JANUARY_28);
        HyperdriveModule otherModule = createModule("HY-2-B", JANUARY_27, JANUARY_30);

        assertTrue(hyperdriveModule.isActiveAtSameTimeAs(otherModule));
    }

    @Test
    void givenOtherModuleDeactivatedDuringModule_whenIsActiveAtSameTimeAs_thenReturnTrue() {
        HyperdriveModule hyperdriveModule = createModule("HY-1-A", JANUARY_27, JANUARY_29);
        HyperdriveModule otherModule = createModule("HY-2-B", JANUARY_26, JANUARY_28);

        assertTrue(hyperdriveModule.isActiveAtSameTimeAs(otherModule));
    }

    @Test
    void givenOtherModuleActivatedWhenModuleIsDeactivated_whenIsActiveAtSameTimeAs_thenReturnFalse() {
        HyperdriveModule hyperdriveModule = createModule("HY-1-A", JANUARY_26, JANUARY_28);
        HyperdriveModule otherModule = createModule("HY-2-B", JANUARY_28, JANUARY_29);

        assertFalse(hyperdriveModule.isActiveAtSameTimeAs(otherModule));
    }

    @Test
    void givenOtherModuleDeactivatedWhenModuleIsActivated_whenIsActiveAtSameTimeAs_thenReturnFalse() {
        HyperdriveModule hyperdriveModule = createModule("HY-1-A", JANUARY_28, JANUARY_29);
        HyperdriveModule otherModule = createModule("HY-2-B", JANUARY_26, JANUARY_28);

        assertFalse(hyperdriveModule.isActiveAtSameTimeAs(otherModule));
    }

    @Test
    void givenOtherModuleActivatedAfterModule_whenIsActiveAtSameTimeAs_thenReturnFalse() {
        HyperdriveModule hyperdriveModule = createModule("HY-1-A", JANUARY_26, JANUARY_27);
        HyperdriveModule otherModule = createModule("HY-2-B", JANUARY_29, JANUARY_30);

        assertFalse(hyperdriveModule.isActiveAtSameTimeAs(otherModule));
    }

    private static CruiseDateTime dateTime(int dayOfJanuary) {
        return new CruiseDateTime(LocalDateTime.of(2085, 1, dayOfJanuary, 1, 0));
    }

    private HyperdriveModule createModule(String id, CruiseDateTime activationDateTime,
                                          CruiseDateTime deactivationDateTime) {
        return new HyperdriveModule(new HyperdriveModuleId(id), ANY_POWER_LEVEL, activationDateTime,
                                    deactivationDateTime);
    }
}
