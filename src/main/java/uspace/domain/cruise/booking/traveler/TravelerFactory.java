package uspace.domain.cruise.booking.traveler;

import java.util.ArrayList;

public class TravelerFactory {
    public Traveler create(String travelerNameStr, TravelerCategory travelerCategory) {
        TravelerId travelerId = new TravelerId();
        TravelerName travelerName = new TravelerName(travelerNameStr);

        return new Traveler(travelerId, travelerName, travelerCategory, new ArrayList<>());
    }
}
