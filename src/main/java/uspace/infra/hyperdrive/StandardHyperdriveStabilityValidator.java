package uspace.infra.hyperdrive;

import standardHyperdriveStabilitySystem.StandardHyperdriveStabilitySystem;
import uspace.domain.cruise.hyperdrive.HyperdriveStabilityValidator;
import uspace.domain.cruise.hyperdrive.module.HyperdriveModuleId;

public class StandardHyperdriveStabilityValidator implements HyperdriveStabilityValidator {
    private final StandardHyperdriveStabilitySystem standardHyperdriveStabilitySystem;

    public StandardHyperdriveStabilityValidator() {
        this.standardHyperdriveStabilitySystem = new StandardHyperdriveStabilitySystem();
    }

    @Override
    public boolean isStable(HyperdriveModuleId hyperdriveModuleId) {
        return standardHyperdriveStabilitySystem.isHyperdriveModuleStable(hyperdriveModuleId.toString());
    }
}
