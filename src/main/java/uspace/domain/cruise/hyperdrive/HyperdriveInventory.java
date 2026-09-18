package uspace.domain.cruise.hyperdrive;

import uspace.domain.cruise.hyperdrive.module.HyperdriveModule;

import java.util.List;

public class HyperdriveInventory {
    private final List<HyperdriveModule> hyperdriveModules;

    public HyperdriveInventory(List<HyperdriveModule> hyperdriveModules) {
        this.hyperdriveModules = hyperdriveModules;
    }

    public List<HyperdriveModule> getAllHyperdriveModules() {
        return hyperdriveModules;
    }
}
