package strategy;

import controller.GameEngine;
import models.Player;
import models.orders.Order;
import models.worldmap.Country;

public class HumanStrategy implements Strategy{
    private Player d_player;
    GameEngine d_gameEngine;
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

    public HumanStrategy(Player p_player, GameEngine p_gameEngine)
    {
         d_player = p_player;
        d_gameEngine = p_gameEngine;
    }
}
