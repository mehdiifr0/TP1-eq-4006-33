package uspace.domain.cruise.booking.traveler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.domain.cruise.booking.traveler.badge.Badge;
import uspace.domain.cruise.booking.traveler.exceptions.ZeroGravityExperienceChildCriteriaException;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChildTravelerTest {
    private static final TravelerId TRAVELER_ID = new TravelerId("trav-1");

    private static final TravelerName ANY_NAME = new TravelerName("Bob");

    @Mock
    private ZeroGravityExperience zeroGravityExperienceMock;

    @Mock
    private Traveler otherTravelerMock;

    private List<Badge> badges;

    private List<Traveler> bookingTravelers;

    private ChildTraveler childTraveler;

    @BeforeEach
    void createChildTraveler() {
        badges = new ArrayList<>();
        childTraveler = new ChildTraveler(TRAVELER_ID, ANY_NAME, badges);
        bookingTravelers = new ArrayList<>();
        bookingTravelers.add(childTraveler);
        bookingTravelers.add(otherTravelerMock);
    }

    @Test
    void whenGetCategory_thenReturnChild() {
        assertEquals(TravelerCategory.CHILD, childTraveler.getCategory());
    }

    @Test
    void whenCanAccompanyChild_thenReturnFalse() {
        assertFalse(childTraveler.canAccompanyChild());
    }

    @Test
    void givenChildAloneInBooking_whenBookZeroGravityExperience_thenThrowZeroGravityExperienceChildCriteriaException() {
        List<Traveler> childOnly = List.of(childTraveler);

        assertThrows(ZeroGravityExperienceChildCriteriaException.class,
                     () -> childTraveler.bookZeroGravityExperience(zeroGravityExperienceMock, childOnly));
    }

    @Test
    void givenOtherChildInBooking_whenBookZeroGravityExperience_thenThrowZeroGravityExperienceChildCriteriaException() {
        when(otherTravelerMock.canAccompanyChild()).thenReturn(false);

        assertThrows(ZeroGravityExperienceChildCriteriaException.class,
                     () -> childTraveler.bookZeroGravityExperience(zeroGravityExperienceMock, bookingTravelers));
    }

    @Test
    void givenCompanionNotBooked_whenBookZeroGravityExperience_thenThrowZeroGravityExperienceChildCriteriaException() {
        when(otherTravelerMock.canAccompanyChild()).thenReturn(true);
        when(otherTravelerMock.hasBooked(zeroGravityExperienceMock)).thenReturn(false);

        assertThrows(ZeroGravityExperienceChildCriteriaException.class,
                     () -> childTraveler.bookZeroGravityExperience(zeroGravityExperienceMock, bookingTravelers));
    }

    @Test
    void givenCompanionNotBooked_whenBookZeroGravityExperience_thenExperienceIsNotBooked() {
        when(otherTravelerMock.canAccompanyChild()).thenReturn(true);
        when(otherTravelerMock.hasBooked(zeroGravityExperienceMock)).thenReturn(false);

        assertThrows(ZeroGravityExperienceChildCriteriaException.class,
                     () -> childTraveler.bookZeroGravityExperience(zeroGravityExperienceMock, bookingTravelers));

        verify(zeroGravityExperienceMock, never()).book(TRAVELER_ID);
    }

    @Test
    void givenAccompanyingTravelerWhoHasBooked_whenBookZeroGravityExperience_thenExperienceIsBookedForChild() {
        givenAccompanyingTravelerWhoHasBooked();

        childTraveler.bookZeroGravityExperience(zeroGravityExperienceMock, bookingTravelers);

        verify(zeroGravityExperienceMock).book(TRAVELER_ID);
    }

    @Test
    void givenAccompanyingTravelerWhoHasBooked_whenBookZeroGravityExperience_thenEarnMiniZeroGBadge() {
        givenAccompanyingTravelerWhoHasBooked();

        childTraveler.bookZeroGravityExperience(zeroGravityExperienceMock, bookingTravelers);

        assertEquals(List.of(Badge.MINI_ZERO_G), childTraveler.getBadges());
    }

    @Test
    void givenChildWithMiniZeroGBadge_whenBookZeroGravityExperience_thenBadgeIsNotEarnedTwice() {
        givenAccompanyingTravelerWhoHasBooked();
        badges.add(Badge.MINI_ZERO_G);

        childTraveler.bookZeroGravityExperience(zeroGravityExperienceMock, bookingTravelers);

        assertEquals(List.of(Badge.MINI_ZERO_G), childTraveler.getBadges());
    }

    private void givenAccompanyingTravelerWhoHasBooked() {
        when(otherTravelerMock.canAccompanyChild()).thenReturn(true);
        when(otherTravelerMock.hasBooked(zeroGravityExperienceMock)).thenReturn(true);
    }
}
