package models.orders;

import controller.GameEngine;
import models.Player;

/**
 * Represents a blockade order, which is used to blockade a country.
 * This class implements the Order interface.
 */
public class Blockade implements Order{
    Player d_sourcePlayer;
    int d_playerOrderID;
    String d_playerOrderName;
    private final int d_blockadeCountryID;
    GameEngine d_gameEngine;

    /**
     * Validates the blockade command.
     *
     * @return Always returns true.
     */
    @Override
    public boolean validateCommand(){

        if (!d_sourcePlayer.getAssignedCountries().contains(d_blockadeCountryID)) {
            System.out.println("Player does not own the source country");
            return false;
        }
        return true;
    }

    /**
     * Constructs a new Blockade order with the specified parameters.
     *
     * @param p_sourcePlayer      The player who issued the blockade order.
     * @param p_playerOrderID     The ID of the order issued by the player.
     * @param p_playerOrderName   The name of the order.
     * @param p_blockadeCountryID The ID of the country to be blockaded.
     * @param p_gameEngine        The game engine associated with the order.
     */
    public Blockade(Player p_sourcePlayer, int p_playerOrderID, String p_playerOrderName, int p_blockadeCountryID, GameEngine p_gameEngine) {
        this.d_sourcePlayer = p_sourcePlayer;
        this.d_playerOrderID = p_playerOrderID;
        this.d_playerOrderName  = p_playerOrderName;
        this.d_blockadeCountryID = p_blockadeCountryID;
        this.d_gameEngine = p_gameEngine;
    }

    /**
     * Executes the blockade order.
     * If the source player owns the specified country, its reinforcements are tripled,
     * and the country is removed from the source player's assigned countries.
     */
    @Override
    public void execute(){

        int l_currentReinforcementsBlockadeCountry = this.d_gameEngine.d_worldmap.getCountry(this.d_blockadeCountryID).getReinforcements();
        this.d_gameEngine.d_worldmap.getCountry(this.d_blockadeCountryID).setReinforcements(l_currentReinforcementsBlockadeCountry*3);
        this.d_sourcePlayer.removeAssignedCountries(this.d_blockadeCountryID);

    }

}
