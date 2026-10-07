package uspace.domain.cruise.booking.traveler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.domain.cruise.booking.traveler.badge.Badge;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SeniorTravelerTest {
    private static final TravelerId TRAVELER_ID = new TravelerId("trav-1");
    private static final TravelerName ANY_NAME = new TravelerName("Bob");
    private static final List<Traveler> NO_OTHER_TRAVELER = new ArrayList<>();

    @Mock
    private ZeroGravityExperience zeroGravityExperienceMock;

    private List<Badge> badges;
    private SeniorTraveler seniorTraveler;

    @BeforeEach
    void createSeniorTraveler() {
        badges = new ArrayList<>();
        seniorTraveler = new SeniorTraveler(TRAVELER_ID, ANY_NAME, badges);
    }

    @Test
    void whenGetCategory_thenReturnSenior() {
        assertEquals(TravelerCategory.SENIOR, seniorTraveler.getCategory());
    }

    @Test
    void whenCanAccompanyChild_thenReturnTrue() {
        assertTrue(seniorTraveler.canAccompanyChild());
    }

    @Test
    void whenBookZeroGravityExperience_thenExperienceIsBookedForTraveler() {
        seniorTraveler.bookZeroGravityExperience(zeroGravityExperienceMock, NO_OTHER_TRAVELER);

        verify(zeroGravityExperienceMock).book(TRAVELER_ID);
    }

    @Test
    void whenBookZeroGravityExperience_thenEarnZeroGAndStillGotItBadges() {
        seniorTraveler.bookZeroGravityExperience(zeroGravityExperienceMock, NO_OTHER_TRAVELER);

        assertEquals(List.of(Badge.ZERO_G, Badge.STILL_GOT_IT), seniorTraveler.getBadges());
    }

    @Test
    void givenTravelerWithZeroGBadge_whenBookZeroGravityExperience_thenEarnOnlyStillGotItBadge() {
        badges.add(Badge.ZERO_G);

        seniorTraveler.bookZeroGravityExperience(zeroGravityExperienceMock, NO_OTHER_TRAVELER);

        assertEquals(List.of(Badge.ZERO_G, Badge.STILL_GOT_IT), seniorTraveler.getBadges());
    }

    @Test
    void givenTravelerWithBothBadges_whenBookZeroGravityExperience_thenNoBadgeIsEarnedTwice() {
        badges.add(Badge.ZERO_G);
        badges.add(Badge.STILL_GOT_IT);

        seniorTraveler.bookZeroGravityExperience(zeroGravityExperienceMock, NO_OTHER_TRAVELER);

        assertEquals(List.of(Badge.ZERO_G, Badge.STILL_GOT_IT), seniorTraveler.getBadges());
    }
}
