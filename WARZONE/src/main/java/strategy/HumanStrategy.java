package strategy;

import controller.GameEngine;
import models.Player;
import models.orders.Order;
import models.worldmap.Country;

/**
 * Represents a strategy where a human player manually selects actions.
 */
public class HumanStrategy implements Strategy{
    /**
     * The player associated with this strategy.
     */
    private Player d_player;

    /**
     * The game engine associated with this strategy.
     */
    GameEngine d_gameEngine;
    /**
     * Gets the source country for the human player's action.
     * Since this is a human strategy, the source country is not determined programmatically.
     * @return Always returns 0 since the source country is not determined programmatically.
     */
    @Override
    public int getSourceCountry() {
        return 0;
    }

    /**
     * Gets the target country for the human player's action.
     * Since this is a human strategy, the target country is not determined programmatically.
     * @param p_sourceCountryId The ID of the source country.
     * @return Always returns 0 since the target country is not determined programmatically.
     */
    @Override
    public int getTargetCountry(int p_sourceCountryId) {
        return 0;
    }

    /**
     * Creates an order based on the human player's actions.
     * Since this is a human strategy, the order is created manually by the player.
     * @return Always returns null since the order is created manually by the player.
     */
    @Override
    public Order createOrder() {
        return null;
    }

    /**
     * Constructs a HumanStrategy object with the given player and game engine.
     * @param p_player The player associated with this strategy.
     * @param p_gameEngine The game engine associated with this strategy.
     */
    public HumanStrategy(Player p_player, GameEngine p_gameEngine)
    {
         d_player = p_player;
        d_gameEngine = p_gameEngine;
    }
}
