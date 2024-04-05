package strategy;

import models.worldmap.Country;

public class AggressiveStrategy  implements Strategy{
    @Override
    public Country getSourceCountry() {
        return null;
    }

    @Override
    public Country getTargetCountry() {
        return null;
    }
}
