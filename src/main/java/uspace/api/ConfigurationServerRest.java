package uspace.api;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import uspace.api.cruise.booking.NewBookingDtoValidator;
import uspace.application.cruise.hyperdrive.HyperdriveModuleAssembler;
import uspace.application.utils.dateTimeParser.LocalDateTimeParser;
import uspace.application.cruise.booking.BookingAssembler;
import uspace.application.cruise.booking.BookingService;
import uspace.application.cruise.CruiseAssembler;
import uspace.application.cruise.CruiseService;
import uspace.application.cruise.booking.BookingTravelerAssembler;
import uspace.domain.cruise.CruiseRepository;
import uspace.domain.cruise.booking.BookingFactory;
import uspace.domain.cruise.booking.traveler.TravelerFactory;
import uspace.infra.persistence.inMemory.InMemoryCruiseRepository;

import java.time.format.DateTimeFormatter;

public class ConfigurationServerRest extends AbstractBinder {

    @Override
    protected void configure() {
        bindAsContract(TravelerFactory.class);
        bindAsContract(BookingFactory.class);

        bindAsContract(BookingTravelerAssembler.class);
        bindAsContract(BookingAssembler.class);
        bindAsContract(HyperdriveModuleAssembler.class);
        bindAsContract(CruiseAssembler.class);

        bind(DateTimeFormatter.ISO_LOCAL_DATE_TIME).to(DateTimeFormatter.class);
        bindAsContract(LocalDateTimeParser.class);

        bind(InMemoryCruiseRepository.class).to(CruiseRepository.class);

        bindAsContract(NewBookingDtoValidator.class);

        bindAsContract(BookingService.class);
        bindAsContract(CruiseService.class);
    }
}
