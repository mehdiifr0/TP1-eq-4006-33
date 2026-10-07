package uspace.domain.cruise.booking.traveler;

import uspace.domain.cruise.booking.traveler.badge.Badge;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;

import java.util.List;

public class AdultTraveler extends Traveler {

    public AdultTraveler(TravelerId id, TravelerName name, List<Badge> badges) {
        super(id, name, badges);
    }

    @Override
    public TravelerCategory getCategory() {
        return TravelerCategory.ADULT;
    }

    @Override
    public boolean canAccompanyChild() {
        return true;
    }

    @Override
    public void bookZeroGravityExperience(ZeroGravityExperience zeroGravityExperience, List<Traveler> bookingTravelers) {
        zeroGravityExperience.book(getId());
        earnBadge(Badge.ZERO_G);
    }
}
