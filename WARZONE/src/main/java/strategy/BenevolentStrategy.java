package strategy;

import models.worldmap.Country;

public class BenevolentStrategy implements Strategy{
    @Override
    public int getSourceCountry() {
        return 0;
    }

    @Override
    public int getTargetCountry(int p_sourceCountryId) {
        return 0;
    }
}
