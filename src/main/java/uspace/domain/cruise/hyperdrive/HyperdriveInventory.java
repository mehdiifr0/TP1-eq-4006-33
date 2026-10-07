package uspace.domain.cruise.hyperdrive;

import uspace.domain.cruise.hyperdrive.module.HyperdriveModule;
import uspace.domain.cruise.hyperdrive.module.HyperdriveModuleId;

import java.util.List;

public class HyperdriveInventory {
    private final List<HyperdriveModule> hyperdriveModules;

    public HyperdriveInventory(List<HyperdriveModule> hyperdriveModules) {
        this.hyperdriveModules = hyperdriveModules;
    }

    public List<HyperdriveModule> getAllHyperdriveModules() {
        return hyperdriveModules;
    }

    public boolean contains(HyperdriveModuleId hyperdriveModuleId) {
        for (HyperdriveModule hyperdriveModule : hyperdriveModules) {
            if (hyperdriveModule.getId().equals(hyperdriveModuleId)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasModuleActiveAtSameTimeAs(HyperdriveModule newHyperdriveModule) {
        for (HyperdriveModule hyperdriveModule : hyperdriveModules) {
            if (hyperdriveModule.isActiveAtSameTimeAs(newHyperdriveModule)) {
                return true;
            }
        }

        return false;
    }

    public void add(HyperdriveModule hyperdriveModule) {
        hyperdriveModules.add(hyperdriveModule);
    }
}
