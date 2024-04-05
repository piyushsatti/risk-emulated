package strategy;

import models.orders.Order;

public interface Strategy {
     int getSourceCountry();
     int getTargetCountry(int p_sourceCountryId);
     Order createOrder();
}
