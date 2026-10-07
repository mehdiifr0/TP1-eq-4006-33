package uspace.domain.cruise.booking.traveler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.domain.cruise.booking.traveler.badge.Badge;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceFullException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdultTravelerTest {
    private static final TravelerId TRAVELER_ID = new TravelerId("trav-1");

    private static final TravelerName ANY_NAME = new TravelerName("Bob");

    private static final List<Traveler> NO_OTHER_TRAVELER = new ArrayList<>();

    @Mock
    private ZeroGravityExperience zeroGravityExperienceMock;

    private List<Badge> badges;

    private AdultTraveler adultTraveler;

    @BeforeEach
    void createAdultTraveler() {
        badges = new ArrayList<>();
        adultTraveler = new AdultTraveler(TRAVELER_ID, ANY_NAME, badges);
    }

    @Test
    void whenGetCategory_thenReturnAdult() {
        assertEquals(TravelerCategory.ADULT, adultTraveler.getCategory());
    }

    @Test
    void whenCanAccompanyChild_thenReturnTrue() {
        assertTrue(adultTraveler.canAccompanyChild());
    }

    @Test
    void givenExperienceBookedByTraveler_whenHasBooked_thenReturnTrue() {
        when(zeroGravityExperienceMock.hasTravelerBooked(TRAVELER_ID)).thenReturn(true);

        assertTrue(adultTraveler.hasBooked(zeroGravityExperienceMock));
    }

    @Test
    void givenExperienceNotBookedByTraveler_whenHasBooked_thenReturnFalse() {
        when(zeroGravityExperienceMock.hasTravelerBooked(TRAVELER_ID)).thenReturn(false);

        assertFalse(adultTraveler.hasBooked(zeroGravityExperienceMock));
    }

    @Test
    void whenBookZeroGravityExperience_thenExperienceIsBookedForTraveler() {
        adultTraveler.bookZeroGravityExperience(zeroGravityExperienceMock, NO_OTHER_TRAVELER);

        verify(zeroGravityExperienceMock).book(TRAVELER_ID);
    }

    @Test
    void whenBookZeroGravityExperience_thenEarnZeroGBadge() {
        adultTraveler.bookZeroGravityExperience(zeroGravityExperienceMock, NO_OTHER_TRAVELER);

        assertEquals(List.of(Badge.ZERO_G), adultTraveler.getBadges());
    }

    @Test
    void givenTravelerWithZeroGBadge_whenBookZeroGravityExperience_thenBadgeIsNotEarnedTwice() {
        badges.add(Badge.ZERO_G);

        adultTraveler.bookZeroGravityExperience(zeroGravityExperienceMock, NO_OTHER_TRAVELER);

        assertEquals(List.of(Badge.ZERO_G), adultTraveler.getBadges());
    }

    @Test
    void givenExperienceRefusingBooking_whenBookZeroGravityExperience_thenNoBadgeIsEarned() {
        doThrow(new ZeroGravityExperienceFullException()).when(zeroGravityExperienceMock).book(TRAVELER_ID);

        assertThrows(ZeroGravityExperienceFullException.class,
                     () -> adultTraveler.bookZeroGravityExperience(zeroGravityExperienceMock, NO_OTHER_TRAVELER));

        assertTrue(adultTraveler.getBadges().isEmpty());
    }
}
