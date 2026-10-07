package uspace.application.cruise.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.application.cruise.booking.dtos.newZeroGravityExperienceBooking.NewZeroGravityExperienceBookingDto;
import uspace.application.utils.dateTimeParser.LocalDateTimeParser;
import uspace.domain.cruise.Cruise;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.CruiseRepository;
import uspace.domain.cruise.booking.BookingFactory;
import uspace.domain.cruise.booking.BookingId;
import uspace.domain.cruise.booking.traveler.TravelerId;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.exceptions.CruiseNotFoundException;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceFullException;
import uspace.domain.exceptions.InvalidDateFormatException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    private static final String CRUISE_ID = "JUPITER_MOONS_EXPLORATION_2085";

    private static final String BOOKING_ID = "booking-id";

    private static final String TRAVELER_ID = "traveler-id";

    private static final String EXPERIENCE_BOOKING_DATE_TIME_STR = "2084-04-08T12:30:00";

    private static final LocalDateTime EXPERIENCE_BOOKING_DATE_TIME = LocalDateTime.of(2084, 4, 8, 12, 30);

    private static final NewZeroGravityExperienceBookingDto NEW_ZERO_GRAVITY_EXPERIENCE_BOOKING_DTO =
            new NewZeroGravityExperienceBookingDto(EXPERIENCE_BOOKING_DATE_TIME_STR);

    @Mock
    private CruiseRepository cruiseRepositoryMock;

    @Mock
    private BookingFactory bookingFactoryMock;

    @Mock
    private BookingAssembler bookingAssemblerMock;

    @Mock
    private LocalDateTimeParser localDateTimeParserMock;

    @Mock
    private Cruise cruiseMock;

    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService(cruiseRepositoryMock, bookingFactoryMock, bookingAssemblerMock,
                                            localDateTimeParserMock);
    }

    @Test
    void givenUnknownCruise_whenBookZeroGravityExperience_thenThrowCruiseNotFoundException() {
        when(cruiseRepositoryMock.findById(new CruiseId(CRUISE_ID))).thenReturn(null);

        assertThrows(CruiseNotFoundException.class, this::bookZeroGravityExperience);
    }

    @Test
    void givenValidRequest_whenBookZeroGravityExperience_thenCruiseBooksExperienceForTraveler() {
        givenExistingCruiseAndValidDateTime();

        bookZeroGravityExperience();

        verify(cruiseMock).bookZeroGravityExperience(new BookingId(BOOKING_ID),
                new TravelerId(TRAVELER_ID),
                new CruiseDateTime(EXPERIENCE_BOOKING_DATE_TIME));
    }

    @Test
    void givenValidRequest_whenBookZeroGravityExperience_thenSaveCruise() {
        givenExistingCruiseAndValidDateTime();

        bookZeroGravityExperience();

        verify(cruiseRepositoryMock).save(cruiseMock);
    }

    @Test
    void givenInvalidDateTimeFormat_whenBookZeroGravityExperience_thenDoNotBookExperience() {
        when(cruiseRepositoryMock.findById(new CruiseId(CRUISE_ID))).thenReturn(cruiseMock);
        when(localDateTimeParserMock.parse(EXPERIENCE_BOOKING_DATE_TIME_STR))
                .thenThrow(new InvalidDateFormatException());

        assertThrows(InvalidDateFormatException.class, this::bookZeroGravityExperience);

        verifyNoInteractions(cruiseMock);
    }

    @Test
    void givenCruiseRefusesBooking_whenBookZeroGravityExperience_thenDoNotSaveCruise() {
        givenExistingCruiseAndValidDateTime();
        doThrow(new ZeroGravityExperienceFullException())
                .when(cruiseMock).bookZeroGravityExperience(any(), any(), any());

        assertThrows(ZeroGravityExperienceFullException.class, this::bookZeroGravityExperience);

        verify(cruiseRepositoryMock, never()).save(any());
    }

    private void givenExistingCruiseAndValidDateTime() {
        when(cruiseRepositoryMock.findById(new CruiseId(CRUISE_ID))).thenReturn(cruiseMock);
        when(localDateTimeParserMock.parse(EXPERIENCE_BOOKING_DATE_TIME_STR)).thenReturn(EXPERIENCE_BOOKING_DATE_TIME);
    }

    private void bookZeroGravityExperience() {
        bookingService.bookZeroGravityExperience(CRUISE_ID, BOOKING_ID, TRAVELER_ID,
                                                 NEW_ZERO_GRAVITY_EXPERIENCE_BOOKING_DTO);
    }
}
