package uspace.application.cruise;

import jakarta.inject.Inject;
import uspace.application.cruise.dtos.CruiseDto;
import uspace.application.cruise.hyperdrive.dtos.newHyperdriveModule.NewHyperdriveModuleDto;
import uspace.application.utils.dateParser.LocalDateParser;
import uspace.domain.cruise.Cruise;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.CruiseRepository;
import uspace.domain.cruise.exceptions.CruiseNotFoundException;
import uspace.domain.cruise.hyperdrive.HyperdriveStabilityValidator;
import uspace.domain.cruise.hyperdrive.exceptions.InvalidHyperdriveModuleException;
import uspace.domain.cruise.hyperdrive.module.HyperdriveModule;
import uspace.domain.cruise.hyperdrive.module.HyperdriveModuleFactory;
import uspace.domain.cruise.hyperdrive.module.HyperdriveModuleId;

import java.time.LocalDate;

public class CruiseService {

    private final CruiseRepository cruiseRepository;

    private final CruiseAssembler cruiseAssembler;

    private final HyperdriveModuleFactory hyperdriveModuleFactory;

    private final HyperdriveStabilityValidator hyperdriveStabilityValidator;

    private final LocalDateParser localDateParser;

    @Inject
    public CruiseService(CruiseRepository cruiseRepository, CruiseAssembler cruiseAssembler,
                         HyperdriveModuleFactory hyperdriveModuleFactory,
                         HyperdriveStabilityValidator hyperdriveStabilityValidator, LocalDateParser localDateParser) {
        this.cruiseRepository = cruiseRepository;
        this.cruiseAssembler = cruiseAssembler;
        this.hyperdriveModuleFactory = hyperdriveModuleFactory;
        this.hyperdriveStabilityValidator = hyperdriveStabilityValidator;
        this.localDateParser = localDateParser;
    }

    public CruiseDto findCruise(String cruiseId) {
        Cruise cruise = findCruiseById(cruiseId);

        return cruiseAssembler.toDto(cruise);
    }

    public void addHyperdriveModule(String cruiseId, NewHyperdriveModuleDto newHyperdriveModuleDto) {
        Cruise cruise = findCruiseById(cruiseId);

        HyperdriveModuleId hyperdriveModuleId = new HyperdriveModuleId(newHyperdriveModuleDto.id);
        LocalDate activationDate = localDateParser.parse(newHyperdriveModuleDto.activationDate);
        if (!hyperdriveStabilityValidator.isStable(hyperdriveModuleId)) {
            throw new InvalidHyperdriveModuleException();
        }

        HyperdriveModule hyperdriveModule = hyperdriveModuleFactory.create(hyperdriveModuleId,
                                                                           newHyperdriveModuleDto.powerLevel,
                                                                           newHyperdriveModuleDto.activationDays,
                                                                           activationDate);
        cruise.addHyperdriveModule(hyperdriveModule);
        cruiseRepository.save(cruise);
    }

    private Cruise findCruiseById(String cruiseId) {
        Cruise cruise = cruiseRepository.findById(new CruiseId(cruiseId));
        if (cruise == null) {
            throw new CruiseNotFoundException();
        }
        return cruise;
    }
}
