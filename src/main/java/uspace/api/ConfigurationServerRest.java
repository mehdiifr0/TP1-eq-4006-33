package uspace.api;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import uspace.api.cruise.booking.NewBookingDtoValidator;
import uspace.api.cruise.hyperdrive.NewHyperdriveModuleDtoValidator;
import uspace.application.cruise.hyperdrive.HyperdriveModuleAssembler;
import uspace.application.utils.dateParser.LocalDateParser;
import uspace.application.utils.dateTimeParser.LocalDateTimeParser;
import uspace.application.cruise.booking.BookingAssembler;
import uspace.application.cruise.booking.BookingService;
import uspace.application.cruise.CruiseAssembler;
import uspace.application.cruise.CruiseService;
import uspace.application.cruise.booking.BookingTravelerAssembler;
import uspace.domain.cruise.CruiseRepository;
import uspace.domain.cruise.booking.BookingFactory;
import uspace.domain.cruise.booking.traveler.TravelerFactory;
import uspace.domain.cruise.hyperdrive.HyperdriveStabilityValidator;
import uspace.domain.cruise.hyperdrive.module.HyperdriveModuleFactory;
import uspace.infra.hyperdrive.StandardHyperdriveStabilityValidator;
import uspace.infra.persistence.inMemory.InMemoryCruiseRepository;
import uspace.api.cruise.booking.NewZeroGravityExperienceBookingDtoValidator;

import java.time.format.DateTimeFormatter;

public class ConfigurationServerRest extends AbstractBinder {

    @Override
    protected void configure() {
        bindAsContract(TravelerFactory.class);
        bindAsContract(BookingFactory.class);
        bindAsContract(HyperdriveModuleFactory.class);

        bindAsContract(BookingTravelerAssembler.class);
        bindAsContract(BookingAssembler.class);
        bindAsContract(HyperdriveModuleAssembler.class);
        bindAsContract(CruiseAssembler.class);

        bind(DateTimeFormatter.ISO_LOCAL_DATE_TIME).to(DateTimeFormatter.class);
        bindAsContract(LocalDateTimeParser.class);
        bindAsContract(LocalDateParser.class);

        bind(InMemoryCruiseRepository.class).to(CruiseRepository.class);
        bind(StandardHyperdriveStabilityValidator.class).to(HyperdriveStabilityValidator.class);

        bindAsContract(NewBookingDtoValidator.class);
        bindAsContract(NewZeroGravityExperienceBookingDtoValidator.class);
        bindAsContract(NewHyperdriveModuleDtoValidator.class);

        bindAsContract(BookingService.class);
        bindAsContract(CruiseService.class);
    }
}
