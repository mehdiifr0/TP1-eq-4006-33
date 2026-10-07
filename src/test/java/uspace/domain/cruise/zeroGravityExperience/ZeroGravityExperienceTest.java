package uspace.domain.cruise.zeroGravityExperience;

import org.junit.jupiter.api.Test;
import uspace.domain.cruise.booking.traveler.TravelerId;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceAlreadyBookedException;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceFullException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ZeroGravityExperienceTest {
    private static final int ANY_CAPACITY = 10;

    private static final int NO_CAPACITY = 0;

    private static final int CAPACITY_OF_ONE = 1;

    private static final int CAPACITY_OF_TWO = 2;

    private static final TravelerId TRAVELER_ID = new TravelerId("traveler-id");

    private static final TravelerId OTHER_TRAVELER_ID = new TravelerId("other-traveler-id");

    @Test
    void givenTravelerNotBooked_whenHasTravelerBooked_thenReturnFalse() {
        ZeroGravityExperience zeroGravityExperience = new ZeroGravityExperience(ANY_CAPACITY);

        boolean hasTravelerBooked = zeroGravityExperience.hasTravelerBooked(TRAVELER_ID);

        assertFalse(hasTravelerBooked);
    }

    @Test
    void givenOtherTravelerBooked_whenHasTravelerBooked_thenReturnFalse() {
        ZeroGravityExperience zeroGravityExperience = new ZeroGravityExperience(ANY_CAPACITY);
        zeroGravityExperience.book(OTHER_TRAVELER_ID);

        boolean hasTravelerBooked = zeroGravityExperience.hasTravelerBooked(TRAVELER_ID);

        assertFalse(hasTravelerBooked);
    }

    @Test
    void givenAvailablePlaces_whenBook_thenTravelerHasBooked() {
        ZeroGravityExperience zeroGravityExperience = new ZeroGravityExperience(ANY_CAPACITY);

        zeroGravityExperience.book(TRAVELER_ID);

        assertTrue(zeroGravityExperience.hasTravelerBooked(TRAVELER_ID));
    }

    @Test
    void givenOnePlaceLeft_whenBook_thenTravelerHasBooked() {
        ZeroGravityExperience zeroGravityExperience = new ZeroGravityExperience(CAPACITY_OF_TWO);
        zeroGravityExperience.book(OTHER_TRAVELER_ID);

        zeroGravityExperience.book(TRAVELER_ID);

        assertTrue(zeroGravityExperience.hasTravelerBooked(TRAVELER_ID));
    }

    @Test
    void givenTravelerAlreadyBooked_whenBook_thenThrowZeroGravityExperienceAlreadyBookedException() {
        ZeroGravityExperience zeroGravityExperience = new ZeroGravityExperience(ANY_CAPACITY);
        zeroGravityExperience.book(TRAVELER_ID);

        assertThrows(ZeroGravityExperienceAlreadyBookedException.class, () -> zeroGravityExperience.book(TRAVELER_ID));
    }

    @Test
    void givenFullExperience_whenBook_thenThrowZeroGravityExperienceFullException() {
        ZeroGravityExperience zeroGravityExperience = new ZeroGravityExperience(CAPACITY_OF_ONE);
        zeroGravityExperience.book(OTHER_TRAVELER_ID);

        assertThrows(ZeroGravityExperienceFullException.class, () -> zeroGravityExperience.book(TRAVELER_ID));
    }

    @Test
    void givenFullExperience_whenBook_thenTravelerHasNotBooked() {
        ZeroGravityExperience zeroGravityExperience = new ZeroGravityExperience(CAPACITY_OF_ONE);
        zeroGravityExperience.book(OTHER_TRAVELER_ID);

        assertThrows(ZeroGravityExperienceFullException.class, () -> zeroGravityExperience.book(TRAVELER_ID));

        assertFalse(zeroGravityExperience.hasTravelerBooked(TRAVELER_ID));
    }

    @Test
    void givenNoCapacity_whenBook_thenThrowZeroGravityExperienceFullException() {
        ZeroGravityExperience zeroGravityExperience = new ZeroGravityExperience(NO_CAPACITY);

        assertThrows(ZeroGravityExperienceFullException.class, () -> zeroGravityExperience.book(TRAVELER_ID));
    }

    @Test
    void givenFullExperienceAndTravelerAlreadyBooked_whenBook_thenThrowZeroGravityExperienceAlreadyBookedException() {
        ZeroGravityExperience zeroGravityExperience = new ZeroGravityExperience(CAPACITY_OF_ONE);
        zeroGravityExperience.book(TRAVELER_ID);

        assertThrows(ZeroGravityExperienceAlreadyBookedException.class, () -> zeroGravityExperience.book(TRAVELER_ID));
    }
}
