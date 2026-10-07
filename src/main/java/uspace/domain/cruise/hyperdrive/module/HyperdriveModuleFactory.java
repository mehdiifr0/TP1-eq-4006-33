package uspace.domain.cruise.hyperdrive.module;

import uspace.domain.cruise.dateTime.CruiseDateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class HyperdriveModuleFactory {
    private static final LocalTime ACTIVATION_TIME = LocalTime.of(1, 0);

    public HyperdriveModule create(HyperdriveModuleId id, int powerLevel, int activationDays, LocalDate activationDate) {
        LocalDateTime activationDateTime = activationDate.atTime(ACTIVATION_TIME);
        LocalDateTime deactivationDateTime = activationDateTime.plusDays(activationDays);

        return new HyperdriveModule(id,
                                    new HyperdrivePowerLevel(powerLevel),
                                    new CruiseDateTime(activationDateTime),
                                    new CruiseDateTime(deactivationDateTime));
    }
}
