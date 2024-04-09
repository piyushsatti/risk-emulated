package models.orders;

import controller.GameEngine;
import models.Player;

/**
 * Represents the "Airlift" order, which allows a player to move troops from one of their countries to any other country on the map.
 */
public class Airlift implements Order {
    /**
     * The player who issued the order.
     */
    Player d_sourcePlayer;

    /**
     * The unique identifier for the player order.
     */
    int d_playerOrderID;
    /**
     * The name of the player order.
     */
    String d_playerOrderName;
    /**
     * The ID of the country from which the troops are airlifted.
     */
    private final int d_fromCountryID;
    /**
     * The ID of the country to which the troops are airlifted.
     */
    private final int d_toCountryID;
    /**
     * The number of troops airlifted in the order.
     */
    private final int d_airliftedTroops;

    /**
     * The game engine associated with the order.
     */
    GameEngine d_gameEngine;

    /**
     * Constructs an Airlift order.
     *
     * @param p_sourcePlayer    The player initiating the airlift order.
     * @param p_playerOrderName The name of the airlift order.
     * @param p_playerOrderID   The ID of the player associated with the airlift order.
     * @param p_fromCountryID   The ID of the country from which troops are airlifted.
     * @param p_toCountryID     The ID of the country to which troops are airlifted.
     * @param p_airliftedTroops The number of troops airlifted.
     * @param p_gameEngine      The game engine instance.
     */
    public Airlift(Player p_sourcePlayer, String p_playerOrderName, int p_playerOrderID, int p_fromCountryID, int p_toCountryID, int p_airliftedTroops, GameEngine p_gameEngine) {
        this.d_sourcePlayer = p_sourcePlayer;
        this.d_playerOrderName = p_playerOrderName;
        this.d_playerOrderID = p_playerOrderID;
        this.d_fromCountryID = p_fromCountryID;
        this.d_toCountryID = p_toCountryID;
        this.d_airliftedTroops = p_airliftedTroops;
        this.d_gameEngine = p_gameEngine;
    }

    /**
     * Validates the airlift command.
     *
     * @return true if the airlift command is valid, false otherwise.
     */
    @Override
    public boolean validateCommand() {

        if (!d_sourcePlayer.getAssignedCountries().contains(d_fromCountryID)) {
            this.d_gameEngine.d_renderer.renderError("Player does not own the source country");
            return false;
        }
        if (!d_sourcePlayer.getAssignedCountries().contains(d_toCountryID)) {
            this.d_gameEngine.d_renderer.renderError("Player does not own the target country");
            return false;
        }

        if (d_fromCountryID == d_toCountryID) {
            this.d_gameEngine.d_renderer.renderError("Player cannot Airlift in the same country");
            return false;
        }
        return true;
    }

    /**
     * Executes the airlift command, transferring troops from one country to another.
     * Checks if the airlifted troops are valid and updates the reinforcements accordingly.
     */
    @Override
    public void execute() {

        int l_currentReinforcementsFromCountry = this.d_gameEngine.d_worldmap.getCountry(this.d_fromCountryID).getReinforcements();
        if (this.d_airliftedTroops <= l_currentReinforcementsFromCountry && this.d_airliftedTroops > 0) {
            int l_currentReinforcementsToCountry = this.d_gameEngine.d_worldmap.getCountry(this.d_toCountryID).getReinforcements();
            this.d_gameEngine.d_worldmap.getCountry(this.d_fromCountryID).setReinforcements(l_currentReinforcementsFromCountry - this.d_airliftedTroops);
            this.d_gameEngine.d_worldmap.getCountry(this.d_toCountryID).setReinforcements(l_currentReinforcementsToCountry + this.d_airliftedTroops);
            this.d_gameEngine.d_renderer.renderMessage("Airlift successful " + "From: " + this.d_gameEngine.d_worldmap.getCountry(this.d_fromCountryID).getCountryName() + "To " + this.d_gameEngine.d_worldmap.getCountry(this.d_toCountryID).getCountryName() + "by: " + this.d_sourcePlayer.getName());
            return;
        } else {
            this.d_gameEngine.d_renderer.renderError("Invalid troops. Either troops are not enough or in negative.");
        }
    }
}
