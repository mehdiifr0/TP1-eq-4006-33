package uspace.domain.cruise.booking.traveler;

import uspace.domain.cruise.booking.traveler.badge.Badge;
import uspace.domain.cruise.booking.traveler.exceptions.ZeroGravityExperienceChildCriteriaException;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;

import java.util.List;

public class ChildTraveler extends Traveler {

    public ChildTraveler(TravelerId id, TravelerName name, List<Badge> badges) {
        super(id, name, badges);
    }

    @Override
    public TravelerCategory getCategory() {
        return TravelerCategory.CHILD;
    }

    @Override
    public boolean canAccompanyChild() {
        return false;
    }

    @Override
    public void bookZeroGravityExperience(ZeroGravityExperience zeroGravityExperience,
                                          List<Traveler> bookingTravelers) {
        if (!isAccompanied(zeroGravityExperience, bookingTravelers)) {
            throw new ZeroGravityExperienceChildCriteriaException();
        }

        zeroGravityExperience.book(getId());
        earnBadge(Badge.MINI_ZERO_G);
    }

    private boolean isAccompanied(ZeroGravityExperience zeroGravityExperience, List<Traveler> bookingTravelers) {
        for (Traveler traveler : bookingTravelers) {
            if (traveler.canAccompanyChild() && traveler.hasBooked(zeroGravityExperience)) {
                return true;
            }
        }

        return false;
    }
}
