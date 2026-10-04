package uspace.domain.cruise.booking;

import org.junit.jupiter.api.Test;
import uspace.domain.cruise.booking.exceptions.InvalidBookingDateException;
import uspace.domain.cruise.booking.traveler.Traveler;
import uspace.domain.cruise.booking.traveler.TravelerCategory;
import uspace.domain.cruise.booking.traveler.TravelerId;
import uspace.domain.cruise.booking.traveler.TravelerName;
import uspace.domain.cruise.cabin.CabinType;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.exceptions.NoTravelerToBookException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookingTest {
    private static final BookingId ANY_BOOKING_ID = new BookingId("id-123");
    private static final CruiseDateTime CRUISE_DEPARTURE_DATE_TIME = new CruiseDateTime(LocalDateTime.of(2085, 1, 25, 12, 0));
    private static final CruiseDateTime DATE_TIME_BEFORE_DEPARTURE = new CruiseDateTime(LocalDateTime.of(2085, 1, 25, 11, 55));
    private static final CruiseDateTime DATE_TIME_AFTER_DEPARTURE = new CruiseDateTime(LocalDateTime.of(2085, 1, 25, 12, 5));

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

    private List<Traveler> oneTraveler() {
        List<Traveler> travelers = new ArrayList<>();
        travelers.add(new Traveler(new TravelerId("trav-1"), new TravelerName("Bob"), TravelerCategory.CHILD, new ArrayList<>()));
        return travelers;
    }
}
