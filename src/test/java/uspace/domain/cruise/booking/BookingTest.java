package uspace.domain.cruise.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.application.cruise.booking.BookingAssembler;
import uspace.application.cruise.booking.BookingService;
import uspace.application.cruise.booking.BookingTravelerAssembler;
import uspace.application.cruise.booking.dtos.newBooking.NewBookingDto;
import uspace.application.cruise.booking.dtos.newBooking.NewBookingTravelerDto;
import uspace.application.utils.dateTimeParser.LocalDateTimeParser;
import uspace.domain.cruise.Cruise;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.CruiseRepository;
import uspace.domain.cruise.booking.exceptions.InvalidBookingDateException;
import uspace.domain.cruise.booking.traveler.*;
import uspace.domain.cruise.booking.traveler.badge.Badge;
import uspace.domain.cruise.cabin.CabinType;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.exceptions.NoTravelerToBookException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class BookingTest {
    private static final BookingId ANY_BOOKING_ID = new BookingId("id-123");
    private static final BookingId ANY_OTHER_BOOKING_ID = new BookingId("id-321");
    private static final LocalDateTime ANY_LOCAL_DEPARTURE_DATE_TIME = LocalDateTime.of(2012, 3, 3, 0, 0);
    private static final CruiseDateTime ANY_DEPARTURE_DATE_TIME = new CruiseDateTime(ANY_LOCAL_DEPARTURE_DATE_TIME);
    private static final CruiseDateTime ANY_DATE_TIME_BEFORE_DEPARTURE = new CruiseDateTime(ANY_LOCAL_DEPARTURE_DATE_TIME.minusHours(1));
    @Mock
    private CruiseRepository cruiseRepositoryMock;

    @Test
    void givenBookingWithoutTraveler_whenValidate_thenThrowNoTravelerToBookException(){
        String anyCabinType = "DELUXE";
        List<NewBookingTravelerDto> noTraveler = new ArrayList<>();
        String anyValidBookingDateTime = "2084-04-08T12:30";
        NewBookingDto anyBookingDtoWithoutTraveler = new NewBookingDto(
                anyCabinType,
                noTraveler,
                anyValidBookingDateTime
        );
        assertTrue(anyBookingDtoWithoutTraveler.travelers.isEmpty());
        Cruise cruise = mock(Cruise.class);
        BookingService service = new BookingService(cruiseRepositoryMock, new BookingFactory(new TravelerFactory()), new BookingAssembler(new BookingTravelerAssembler()), new LocalDateTimeParser(
                DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        String anyCruiseId = "123";
        when(cruiseRepositoryMock.findById(new CruiseId(anyCruiseId))).thenReturn(cruise);
        doThrow(new NoTravelerToBookException()).when(cruise).processBooking(any(Booking.class));
        assertThrows(NoTravelerToBookException.class, () -> service.createBooking(anyCruiseId, anyBookingDtoWithoutTraveler));
    }

    @Test
    void givenDeparture5minutesBeforeBookingDateTime_whenValidate_thenThrowInvalidBookingDateException(){
        Traveler t1 = new Traveler(new TravelerId("trav-1"), new TravelerName("Bob"), TravelerCategory.CHILD, new ArrayList<>());
        ArrayList<Traveler> t = new ArrayList<>();
        t.add(t1);
        CruiseDateTime cdt = new CruiseDateTime(LocalDateTime.now());
        Booking b = new Booking(
                new BookingId("id-123"),
                t,
                CabinType.DELUXE,
                cdt
        );
        CruiseDateTime dep = new CruiseDateTime(LocalDateTime.now().minusMinutes(5));
        boolean exception = false;

        try {
            b.validate(dep);
        } catch (InvalidBookingDateException ex) {
            exception = true;
        }

        assertTrue(exception);
    }

    @Test
    void whenValidate_thenDoNotThrow(){
        TravelerId anyTravelerId = new TravelerId("trav-1");
        TravelerName anyTravelerName = new TravelerName("Bob");
        TravelerCategory anyTravelerCategory = TravelerCategory.CHILD;
        List<Badge> anyBadges = new ArrayList<>();
        Traveler traveler = new Traveler(anyTravelerId, anyTravelerName, anyTravelerCategory, anyBadges);
        ArrayList<Traveler> travelers = new ArrayList<>();
        travelers.add(traveler);
        CabinType anyCabinType = CabinType.DELUXE;
        Booking anyValidBooking = new Booking(
                ANY_BOOKING_ID,
                travelers,
                anyCabinType,
                ANY_DATE_TIME_BEFORE_DEPARTURE
        );
        Booking anyOtherValidBooking = new Booking(
                ANY_OTHER_BOOKING_ID,
                travelers,
                anyCabinType,
                ANY_DATE_TIME_BEFORE_DEPARTURE
        );
        List<Booking> validBookingsToTests = List.of(anyValidBooking, anyOtherValidBooking);

        //validates multiple valid bookings
        for (Booking bookingTest : validBookingsToTests) {
            Executable validate = () -> bookingTest.validate(ANY_DEPARTURE_DATE_TIME);

            assertDoesNotThrow(validate);
            assertFalse(bookingTest.getTravelers().isEmpty()); //validates that the travelers are not empty
        }
    }

}