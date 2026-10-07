package uspace.domain.cruise.booking.traveler;

import uspace.domain.cruise.booking.traveler.badge.Badge;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;

import java.util.List;

public class SeniorTraveler extends Traveler {

    public SeniorTraveler(TravelerId id, TravelerName name, List<Badge> badges) {
        super(id, name, badges);
    }

    @Override
    public TravelerCategory getCategory() {
        return TravelerCategory.SENIOR;
    }

    @Override
    public boolean canAccompanyChild() {
        return true;
    }

    @Override
    public void bookZeroGravityExperience(ZeroGravityExperience zeroGravityExperience, List<Traveler> bookingTravelers) {
        zeroGravityExperience.book(getId());
        earnBadge(Badge.ZERO_G);
        earnBadge(Badge.STILL_GOT_IT);
    }
}
