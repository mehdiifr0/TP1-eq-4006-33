package uspace.api.cruise.booking;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.application.cruise.booking.BookingService;
import uspace.application.cruise.booking.dtos.newZeroGravityExperienceBooking.NewZeroGravityExperienceBookingDto;
import uspace.domain.exceptions.MissingParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class BookingResourceTest {
    private static final String CRUISE_ID = "JUPITER_MOONS_EXPLORATION_2085";
    private static final String BOOKING_ID = "booking-id";
    private static final String TRAVELER_ID = "traveler-id";
    private static final NewZeroGravityExperienceBookingDto NEW_ZERO_GRAVITY_EXPERIENCE_BOOKING_DTO =
            new NewZeroGravityExperienceBookingDto("2084-04-08T12:30:00");

    @Mock
    private BookingService bookingServiceMock;
    @Mock
    private NewBookingDtoValidator newBookingDtoValidatorMock;
    @Mock
    private NewZeroGravityExperienceBookingDtoValidator newZeroGravityExperienceBookingDtoValidatorMock;

    private BookingResource bookingResource;

    @BeforeEach
    void setUp() {
        bookingResource = new BookingResource(bookingServiceMock, newBookingDtoValidatorMock,
                newZeroGravityExperienceBookingDtoValidatorMock);
    }

    @Test
    void whenBookZeroGravityExperience_thenReturnOk() {
        Response response = bookZeroGravityExperience();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void whenBookZeroGravityExperience_thenValidateRequest() {
        bookZeroGravityExperience();

        verify(newZeroGravityExperienceBookingDtoValidatorMock).validate(NEW_ZERO_GRAVITY_EXPERIENCE_BOOKING_DTO);
    }

    @Test
    void whenBookZeroGravityExperience_thenServiceBooksExperience() {
        bookZeroGravityExperience();

        verify(bookingServiceMock).bookZeroGravityExperience(CRUISE_ID, BOOKING_ID, TRAVELER_ID,
                NEW_ZERO_GRAVITY_EXPERIENCE_BOOKING_DTO);
    }

    @Test
    void givenInvalidRequest_whenBookZeroGravityExperience_thenServiceIsNotCalled() {
        doThrow(new MissingParameterException("experienceBookingDateTime"))
                .when(newZeroGravityExperienceBookingDtoValidatorMock).validate(NEW_ZERO_GRAVITY_EXPERIENCE_BOOKING_DTO);

        assertThrows(MissingParameterException.class, this::bookZeroGravityExperience);

        verifyNoInteractions(bookingServiceMock);
    }

    private Response bookZeroGravityExperience() {
        return bookingResource.bookZeroGravityExperience(CRUISE_ID, BOOKING_ID, TRAVELER_ID,
                NEW_ZERO_GRAVITY_EXPERIENCE_BOOKING_DTO);
    }
}