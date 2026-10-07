package uspace.domain.cruise.hyperdrive.module;

import uspace.domain.cruise.dateTime.CruiseDateTime;

public class HyperdriveModule {
    private final HyperdriveModuleId id;
    private final HyperdrivePowerLevel powerLevel;
    private final CruiseDateTime activationDateTime;
    private final CruiseDateTime deactivationDateTime;

    public HyperdriveModule(HyperdriveModuleId id, HyperdrivePowerLevel powerLevel, CruiseDateTime activationDateTime,
                            CruiseDateTime deactivationDateTime) {
        this.id = id;
        this.powerLevel = powerLevel;
        this.activationDateTime = activationDateTime;
        this.deactivationDateTime = deactivationDateTime;
    }

    public HyperdriveModuleId getId() {
        return id;
    }

    public HyperdrivePowerLevel getPowerLevel() {
        return powerLevel;
    }

    public CruiseDateTime getActivationDateTime() {
        return activationDateTime;
    }

    public CruiseDateTime getDeactivationDateTime() {
        return deactivationDateTime;
    }

    public boolean isActiveBetween(CruiseDateTime startDateTime, CruiseDateTime endDateTime) {
        return activationDateTime.isAfter(startDateTime) && deactivationDateTime.isBefore(endDateTime);
    }

    public boolean isActiveAtSameTimeAs(HyperdriveModule otherModule) {
        return activationDateTime.isBefore(otherModule.deactivationDateTime)
               && otherModule.activationDateTime.isBefore(deactivationDateTime);
    }
}
