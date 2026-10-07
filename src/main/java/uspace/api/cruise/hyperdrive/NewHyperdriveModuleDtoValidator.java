package uspace.api.cruise.hyperdrive;

import uspace.application.cruise.hyperdrive.dtos.newHyperdriveModule.NewHyperdriveModuleDto;
import uspace.domain.exceptions.InvalidParameterException;
import uspace.domain.exceptions.MissingParameterException;

public class NewHyperdriveModuleDtoValidator {
    private static final int MISSING_NUMBER = 0;

    public void validate(NewHyperdriveModuleDto newHyperdriveModuleDto) {
        if (newHyperdriveModuleDto == null || newHyperdriveModuleDto.id == null) {
            throw new MissingParameterException("id");
        }
        if (newHyperdriveModuleDto.powerLevel == MISSING_NUMBER) {
            throw new MissingParameterException("powerLevel");
        }
        if (newHyperdriveModuleDto.activationDays == MISSING_NUMBER) {
            throw new MissingParameterException("activationDays");
        }
        if (newHyperdriveModuleDto.activationDate == null) {
            throw new MissingParameterException("activationDate");
        }
        if (newHyperdriveModuleDto.powerLevel < 0) {
            throw new InvalidParameterException("Power level must be a positive number");
        }
        if (newHyperdriveModuleDto.activationDays < 0) {
            throw new InvalidParameterException("Activation days must be a positive number");
        }
    }
}
