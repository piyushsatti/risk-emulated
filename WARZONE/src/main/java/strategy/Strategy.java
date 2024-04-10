package strategy;

import helpers.exceptions.InvalidCommandException;
import models.orders.Order;

/**
 * Interface representing different strategies for players in the game.
 */
public interface Strategy {
    /**
     * Gets the source country for the strategy.
     *
     * @return The ID of the source country.
     */
    int getSourceCountry();

    /**
     * Gets the target country for the strategy based on the given source country.
     *
     * @param p_sourceCountryId The ID of the source country.
     * @return The ID of the target country.
     */
    int getTargetCountry(int p_sourceCountryId);

    /**
     * Creates an order based on the strategy.
     * @return The order created based on the strategy.
     * @throws InvalidCommandException
     */
    Order createOrder() throws InvalidCommandException;

    /**
     * Retrieves the name of the strategy.
     *
     * @return The name of the strategy.
     */
    String getStrategyName();
}
