package strategy;

import models.worldmap.Country;

public class CheaterStrategy implements Strategy{
    @Override
    public Country getSourceCountry() {
        return null;
    }

    @Override
    public Country getTargetCountry() {
        return null;
    }
}
