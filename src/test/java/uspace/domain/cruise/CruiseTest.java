package uspace.domain.cruise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.domain.cruise.booking.Booking;
import uspace.domain.cruise.booking.BookingId;
import uspace.domain.cruise.booking.Bookings;
import uspace.domain.cruise.booking.traveler.TravelerId;
import uspace.domain.cruise.booking.traveler.exceptions.TravelerNotFoundException;
import uspace.domain.cruise.cabin.CabinAvailabilities;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.hyperdrive.HyperdriveInventory;
import uspace.domain.cruise.hyperdrive.module.HyperdriveModule;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceBookingTimeException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class CruiseTest {
    private static final CruiseId CRUISE_ID = new CruiseId("JUPITER_MOON_EXPLORATION_2085");
    private static final CruiseDateTime DEPARTURE_DATE_TIME = new CruiseDateTime(LocalDateTime.of(2085, 1, 25, 12, 0));
    private static final CruiseDateTime END_DATE_TIME = new CruiseDateTime(LocalDateTime.of(2085, 2, 1, 12, 0));
    private static final CruiseDateTime DATE_TIME_BEFORE_DEPARTURE = new CruiseDateTime(LocalDateTime.of(2085, 1, 25, 11, 59));
    private static final CruiseDateTime DATE_TIME_AFTER_DEPARTURE = new CruiseDateTime(LocalDateTime.of(2085, 1, 25, 12, 1));
    private static final BookingId BOOKING_ID = new BookingId("booking-id");
    private static final TravelerId TRAVELER_ID = new TravelerId("traveler-id");
    @Mock
    private HyperdriveInventory hyperdriveInventoryMock;
    @Mock
    private CabinAvailabilities cabinAvailabilitiesMock;
    @Mock
    private Bookings bookingsMock;
    @Mock
    private ZeroGravityExperience zeroGravityExperienceMock;
    @Mock
    private HyperdriveModule hyperdriveModuleMock;
    @Mock
    private Booking bookingMock;

    private Cruise cruise;

    @BeforeEach
    void setup() {
        cruise = new Cruise(CRUISE_ID,
                DEPARTURE_DATE_TIME,
                END_DATE_TIME,
                cabinAvailabilitiesMock,
                bookingsMock,
                zeroGravityExperienceMock,
                hyperdriveInventoryMock);
    }

    @Test
    void givenModulesInInventory_whenGetHyperdriveModules_thenReturnInventoryModules() {
        List<HyperdriveModule> inventoryModules = List.of(hyperdriveModuleMock);
        when(hyperdriveInventoryMock.getAllHyperdriveModules()).thenReturn(inventoryModules);

        List<HyperdriveModule> hyperdriveModules = cruise.getHyperdriveModules();

        assertEquals(inventoryModules, hyperdriveModules);
    }

    @Test
    void givenExperienceBookingDateTimeAfterDeparture_whenBookZeroGravityExperience_thenThrowZeroGravityExperienceBookingTimeException() {
        assertThrows(ZeroGravityExperienceBookingTimeException.class,
                () -> cruise.bookZeroGravityExperience(BOOKING_ID, TRAVELER_ID, DATE_TIME_AFTER_DEPARTURE));
    }

    @Test
    void givenExperienceBookingDateTimeAfterDeparture_whenBookZeroGravityExperience_thenDoNotLookForBooking() {
        assertThrows(ZeroGravityExperienceBookingTimeException.class,
                () -> cruise.bookZeroGravityExperience(BOOKING_ID, TRAVELER_ID, DATE_TIME_AFTER_DEPARTURE));

        verifyNoInteractions(bookingsMock);
    }

    @Test
    void givenUnknownBooking_whenBookZeroGravityExperience_thenThrowTravelerNotFoundException() {
        when(bookingsMock.findById(BOOKING_ID)).thenReturn(null);

        assertThrows(TravelerNotFoundException.class,
                () -> cruise.bookZeroGravityExperience(BOOKING_ID, TRAVELER_ID, DATE_TIME_BEFORE_DEPARTURE));
    }

    @Test
    void givenExistingBookingAndDateTimeBeforeDeparture_whenBookZeroGravityExperience_thenBookingBooksCruiseExperienceForTraveler() {
        when(bookingsMock.findById(BOOKING_ID)).thenReturn(bookingMock);

        cruise.bookZeroGravityExperience(BOOKING_ID, TRAVELER_ID, DATE_TIME_BEFORE_DEPARTURE);

        verify(bookingMock).bookZeroGravityExperience(TRAVELER_ID, zeroGravityExperienceMock);
    }

    @Test
    void givenExistingBookingAndDateTimeEqualToDeparture_whenBookZeroGravityExperience_thenBookingBooksCruiseExperienceForTraveler() {
        when(bookingsMock.findById(BOOKING_ID)).thenReturn(bookingMock);

        cruise.bookZeroGravityExperience(BOOKING_ID, TRAVELER_ID, DEPARTURE_DATE_TIME);

        verify(bookingMock).bookZeroGravityExperience(TRAVELER_ID, zeroGravityExperienceMock);
    }
}