package uspace.domain.cruise.hyperdrive;

import uspace.domain.cruise.hyperdrive.module.HyperdriveModuleId;

public interface HyperdriveStabilityValidator {
    boolean isStable(HyperdriveModuleId hyperdriveModuleId);
}
