package strategy;

import models.worldmap.Country;

public class CheaterStrategy implements Strategy{
    @Override
    public int getSourceCountry() {
        return null;
    }

    @Override
    public int getTargetCountry() {
        return null;
    }
}
