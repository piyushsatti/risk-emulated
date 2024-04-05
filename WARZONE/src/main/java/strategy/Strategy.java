package strategy;

import models.worldmap.Country;

public interface Strategy {
     Country getSourceCountry();
     Country getTargetCountry();
}
