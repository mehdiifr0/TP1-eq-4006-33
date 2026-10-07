package uspace.domain.cruise.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.domain.cruise.booking.exceptions.InvalidBookingDateException;
import uspace.domain.cruise.booking.traveler.Traveler;
import uspace.domain.cruise.booking.traveler.TravelerId;
import uspace.domain.cruise.booking.traveler.exceptions.TravelerNotFoundException;
import uspace.domain.cruise.cabin.CabinType;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.exceptions.NoTravelerToBookException;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingTest {
    private static final BookingId ANY_BOOKING_ID = new BookingId("id-123");
    private static final TravelerId TRAVELER_ID = new TravelerId("trav-1");
    private static final TravelerId UNKNOWN_TRAVELER_ID = new TravelerId("trav-2");
    private static final CruiseDateTime CRUISE_DEPARTURE_DATE_TIME = new CruiseDateTime(LocalDateTime.of(2085, 1, 25, 12, 0));
    private static final CruiseDateTime DATE_TIME_BEFORE_DEPARTURE = new CruiseDateTime(LocalDateTime.of(2085, 1, 25, 11, 55));
    private static final CruiseDateTime DATE_TIME_AFTER_DEPARTURE = new CruiseDateTime(LocalDateTime.of(2085, 1, 25, 12, 5));

    @Mock
    private Traveler travelerMock;
    @Mock
    private ZeroGravityExperience zeroGravityExperienceMock;

    @Test
    void givenBookingWithoutTraveler_whenValidate_thenThrowNoTravelerToBookException() {
        Booking booking = new Booking(ANY_BOOKING_ID, new ArrayList<>(), CabinType.DELUXE, DATE_TIME_BEFORE_DEPARTURE);

        assertThrows(NoTravelerToBookException.class, () -> booking.validate(CRUISE_DEPARTURE_DATE_TIME));
    }

    @Test
    void givenBookingDateTimeAfterCruiseDeparture_whenValidate_thenThrowInvalidBookingDateException() {
        Booking booking = new Booking(ANY_BOOKING_ID, oneTraveler(), CabinType.DELUXE, DATE_TIME_AFTER_DEPARTURE);

        assertThrows(InvalidBookingDateException.class, () -> booking.validate(CRUISE_DEPARTURE_DATE_TIME));
    }

    @Test
    void givenBookingDateTimeEqualToCruiseDeparture_whenValidate_thenThrowInvalidBookingDateException() {
        Booking booking = new Booking(ANY_BOOKING_ID, oneTraveler(), CabinType.DELUXE, CRUISE_DEPARTURE_DATE_TIME);

        assertThrows(InvalidBookingDateException.class, () -> booking.validate(CRUISE_DEPARTURE_DATE_TIME));
    }

    @Test
    void givenBookingDateTimeBeforeCruiseDepartureWithTravelers_whenValidate_thenDoesNotThrow() {
        Booking booking = new Booking(ANY_BOOKING_ID, oneTraveler(), CabinType.DELUXE, DATE_TIME_BEFORE_DEPARTURE);

        assertDoesNotThrow(() -> booking.validate(CRUISE_DEPARTURE_DATE_TIME));
    }

    @Test
    void givenTravelerNotInBooking_whenBookZeroGravityExperience_thenThrowTravelerNotFoundException() {
        when(travelerMock.getId()).thenReturn(TRAVELER_ID);
        Booking booking = new Booking(ANY_BOOKING_ID, oneTraveler(), CabinType.DELUXE, DATE_TIME_BEFORE_DEPARTURE);

        assertThrows(TravelerNotFoundException.class,
                     () -> booking.bookZeroGravityExperience(UNKNOWN_TRAVELER_ID, zeroGravityExperienceMock));
    }

    @Test
    void givenTravelerInBooking_whenBookZeroGravityExperience_thenTravelerBooksExperienceWithBookingTravelers() {
        when(travelerMock.getId()).thenReturn(TRAVELER_ID);
        List<Traveler> travelers = oneTraveler();
        Booking booking = new Booking(ANY_BOOKING_ID, travelers, CabinType.DELUXE, DATE_TIME_BEFORE_DEPARTURE);

        booking.bookZeroGravityExperience(TRAVELER_ID, zeroGravityExperienceMock);

        verify(travelerMock).bookZeroGravityExperience(zeroGravityExperienceMock, travelers);
    }

    private List<Traveler> oneTraveler() {
        List<Traveler> travelers = new ArrayList<>();
        travelers.add(travelerMock);
        return travelers;
    }
}
