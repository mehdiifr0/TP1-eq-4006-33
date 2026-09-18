package uspace.domain.cruise.booking.traveler;

import uspace.domain.cruise.booking.traveler.badge.Badge;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;

import java.util.List;

public class Traveler {
    private final TravelerId id;
    private final TravelerName name;
    private final TravelerCategory category;
    private final List<Badge> badges;

    public Traveler(TravelerId id, TravelerName name, TravelerCategory category, List<Badge> badges)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.badges = badges;
    }

    public TravelerId getId() {
        return id;
    }

    public TravelerName getName() {
        return name;
    }

    public TravelerCategory getCategory() {
        return category;
    }

    public List<Badge> getBadges() {
        return badges;
    }

    public void bookZeroGravityExperience(ZeroGravityExperience zeroGravityExperience)
    {
        zeroGravityExperience.book(id);
        earnBadge(Badge.ZERO_G);
    }

    private void earnBadge(Badge badge) {
        if (!badges.contains(badge))
        {
            badges.add(badge);
        }
    }
}
