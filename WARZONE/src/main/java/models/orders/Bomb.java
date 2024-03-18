package models.orders;

import controller.GameEngine;
import models.Player;

/**
 * Represents a bomb order, which is used to bomb a country.
 * This class implements the Order interface.
 */
public class Bomb  implements  Order{
    Player d_sourcePlayer;
    int d_playerOrderID;
    String d_playerOrderName;
    GameEngine d_gameEngine;
    Player d_targetPlayer;
    private final int d_bombCountryID;

    /**
     * Constructs a new Bomb order with the specified parameters.
     *
     * @param p_sourcePlayer    The player who issued the bomb order.
     * @param p_targetPlayer    The player who owns the target country.
     * @param p_playerOrderID   The ID of the order issued by the player.
     * @param p_playerOrderName The name of the order.
     * @param p_bombCountryID   The ID of the country to be bombed.
     * @param p_gameEngine      The game engine associated with the order.
     */
    public Bomb(Player p_sourcePlayer, Player p_targetPlayer,int p_playerOrderID, String p_playerOrderName,  int p_bombCountryID, GameEngine p_gameEngine) {
        this.d_sourcePlayer = p_sourcePlayer;
        this.d_targetPlayer = p_targetPlayer;
        this.d_playerOrderID = p_playerOrderID;
        this.d_playerOrderName = p_playerOrderName;
        this.d_bombCountryID = p_bombCountryID;
        this.d_gameEngine = p_gameEngine;

    }

    /**
     * Checks if the source player owns any neighboring country of the target country.
     *
     * @return True if the source player owns a neighboring country of the target country, false otherwise.
     */
    public boolean checkIfNeighbour() {
        for(int countryIDs: this.d_sourcePlayer.getAssignedCountries()){
            if(this.d_gameEngine.d_worldmap.getCountry(countryIDs).getBorderCountries().containsKey(this.d_bombCountryID)){
                return true;
            }
        }
        return false;
   }

    /**
     * Validates the bomb command to ensure it meets the necessary conditions.
     *
     * @return True if the bomb command is valid, false otherwise.
     */
    @Override
    public boolean validateCommand(){

        if(this.d_sourcePlayer.getAssignedCountries().contains(d_bombCountryID)){
            System.out.println("Cannot bomb own country!");
            return false;
        }
        if(!checkIfNeighbour()) {
            System.out.println("Cannot Bomb non neighbouring countries");
            return false;
        }

        return true;
    }

    /**
     * Executes the bomb command, reducing the reinforcements of the target country by half, unless negotiated with the target player.
     * If the source player has negotiated with the target player, the execute method is skipped.
     */
    @Override
    public void execute(){
        if(d_targetPlayer != null) {
            if (d_sourcePlayer.getListOfNegotiatedPlayers().contains(d_targetPlayer)) {
                System.out.println(d_sourcePlayer.getName() + " has negotiated with " + d_targetPlayer.getName());
                //skip execute
                return;
            }
        }
            int l_currentReinforcementsBombCountry = this.d_gameEngine.d_worldmap.getCountry(this.d_bombCountryID).getReinforcements();
            this.d_gameEngine.d_worldmap.getCountry(this.d_bombCountryID).setReinforcements(l_currentReinforcementsBombCountry/2);

    }
}
