package uspace.domain.cruise.booking.traveler;

import uspace.domain.cruise.booking.traveler.badge.Badge;

import java.util.ArrayList;
import java.util.List;

public class TravelerFactory {
    public Traveler create(String travelerNameStr, TravelerCategory travelerCategory) {
        TravelerId travelerId = new TravelerId();
        TravelerName travelerName = new TravelerName(travelerNameStr);
        List<Badge> badges = new ArrayList<>();

        return switch (travelerCategory) {
            case ADULT -> new AdultTraveler(travelerId, travelerName, badges);
            case CHILD -> new ChildTraveler(travelerId, travelerName, badges);
            case SENIOR -> new SeniorTraveler(travelerId, travelerName, badges);
        };
    }
}
