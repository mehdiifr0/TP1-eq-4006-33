package uspace.domain.cruise.booking.traveler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TravelerFactoryTest {
    private static final String ANY_NAME = "Bob";

    private TravelerFactory travelerFactory;

    @BeforeEach
    void createTravelerFactory() {
        travelerFactory = new TravelerFactory();
    }

    @Test
    void givenAdultCategory_whenCreate_thenReturnAdultTraveler() {
        Traveler traveler = travelerFactory.create(ANY_NAME, TravelerCategory.ADULT);

        assertInstanceOf(AdultTraveler.class, traveler);
    }

    @Test
    void givenChildCategory_whenCreate_thenReturnChildTraveler() {
        Traveler traveler = travelerFactory.create(ANY_NAME, TravelerCategory.CHILD);

        assertInstanceOf(ChildTraveler.class, traveler);
    }

    @Test
    void givenSeniorCategory_whenCreate_thenReturnSeniorTraveler() {
        Traveler traveler = travelerFactory.create(ANY_NAME, TravelerCategory.SENIOR);

        assertInstanceOf(SeniorTraveler.class, traveler);
    }

    @Test
    void whenCreate_thenTravelerHasGivenName() {
        Traveler traveler = travelerFactory.create(ANY_NAME, TravelerCategory.ADULT);

        assertEquals(new TravelerName(ANY_NAME), traveler.getName());
    }

    @Test
    void whenCreate_thenTravelerHasNoBadge() {
        Traveler traveler = travelerFactory.create(ANY_NAME, TravelerCategory.ADULT);

        assertTrue(traveler.getBadges().isEmpty());
    }
}
