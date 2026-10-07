package uspace.domain.cruise.hyperdrive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.domain.cruise.hyperdrive.module.HyperdriveModule;
import uspace.domain.cruise.hyperdrive.module.HyperdriveModuleId;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HyperdriveInventoryTest {
    private static final HyperdriveModuleId MODULE_ID = new HyperdriveModuleId("HY-77-V");
    private static final HyperdriveModuleId OTHER_MODULE_ID = new HyperdriveModuleId("HY-78-W");

    @Mock
    private HyperdriveModule hyperdriveModuleMock;
    @Mock
    private HyperdriveModule newHyperdriveModuleMock;

    private HyperdriveInventory hyperdriveInventory;

    @BeforeEach
    void createHyperdriveInventory() {
        hyperdriveInventory = new HyperdriveInventory(new ArrayList<>());
    }

    @Test
    void whenAdd_thenModuleIsInInventory() {
        hyperdriveInventory.add(hyperdriveModuleMock);

        assertEquals(List.of(hyperdriveModuleMock), hyperdriveInventory.getAllHyperdriveModules());
    }

    @Test
    void givenEmptyInventory_whenContains_thenReturnFalse() {
        assertFalse(hyperdriveInventory.contains(MODULE_ID));
    }

    @Test
    void givenModuleInInventory_whenContains_thenReturnTrue() {
        when(hyperdriveModuleMock.getId()).thenReturn(MODULE_ID);
        hyperdriveInventory.add(hyperdriveModuleMock);

        assertTrue(hyperdriveInventory.contains(MODULE_ID));
    }

    @Test
    void givenOnlyAnotherModuleInInventory_whenContains_thenReturnFalse() {
        when(hyperdriveModuleMock.getId()).thenReturn(OTHER_MODULE_ID);
        hyperdriveInventory.add(hyperdriveModuleMock);

        assertFalse(hyperdriveInventory.contains(MODULE_ID));
    }

    @Test
    void givenEmptyInventory_whenHasModuleActiveAtSameTimeAs_thenReturnFalse() {
        assertFalse(hyperdriveInventory.hasModuleActiveAtSameTimeAs(newHyperdriveModuleMock));
    }

    @Test
    void givenModuleActiveAtSameTime_whenHasModuleActiveAtSameTimeAs_thenReturnTrue() {
        when(hyperdriveModuleMock.isActiveAtSameTimeAs(newHyperdriveModuleMock)).thenReturn(true);
        hyperdriveInventory.add(hyperdriveModuleMock);

        assertTrue(hyperdriveInventory.hasModuleActiveAtSameTimeAs(newHyperdriveModuleMock));
    }

    @Test
    void givenNoModuleActiveAtSameTime_whenHasModuleActiveAtSameTimeAs_thenReturnFalse() {
        when(hyperdriveModuleMock.isActiveAtSameTimeAs(newHyperdriveModuleMock)).thenReturn(false);
        hyperdriveInventory.add(hyperdriveModuleMock);

        assertFalse(hyperdriveInventory.hasModuleActiveAtSameTimeAs(newHyperdriveModuleMock));
    }
}
