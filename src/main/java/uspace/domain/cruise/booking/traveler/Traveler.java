package uspace.domain.cruise.booking.traveler;

import uspace.domain.cruise.booking.traveler.badge.Badge;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;

import java.util.List;

public abstract class Traveler {
    private final TravelerId id;

    private final TravelerName name;

    private final List<Badge> badges;

    public Traveler(TravelerId id, TravelerName name, List<Badge> badges) {
        this.id = id;
        this.name = name;
        this.badges = badges;
    }

    public TravelerId getId() {
        return id;
    }

    public TravelerName getName() {
        return name;
    }

    public List<Badge> getBadges() {
        return badges;
    }

    public abstract TravelerCategory getCategory();

    public abstract boolean canAccompanyChild();

    public abstract void bookZeroGravityExperience(ZeroGravityExperience zeroGravityExperience,
                                                   List<Traveler> bookingTravelers);

    public boolean hasBooked(ZeroGravityExperience zeroGravityExperience) {
        return zeroGravityExperience.hasTravelerBooked(id);
    }

    protected void earnBadge(Badge badge) {
        if (!badges.contains(badge)) {
            badges.add(badge);
        }
    }
}
