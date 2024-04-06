package strategy;

import models.orders.Order;
import models.worldmap.Country;

public class HumanStrategy implements Strategy{
    @Override
    public int getSourceCountry() {
        return 0;
    }

    @Override
    public int getTargetCountry(int p_sourceCountryId) {
        return 0;
    }

    @Override
    public Order createOrder() {
        return null;
    }
}
