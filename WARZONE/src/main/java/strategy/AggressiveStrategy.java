package strategy;

import models.worldmap.Country;

public class AggressiveStrategy  implements Strategy{
    @Override
    public int getCountryToAttackFrom() {
        return 0;
    }

    @Override
    public int getTargetCountry() {
        return 0;
    }
}
