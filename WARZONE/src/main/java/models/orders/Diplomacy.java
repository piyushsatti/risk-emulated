package models.orders;

import models.Player;
import view.TerminalRenderer;

/**
 * Represents a diplomacy order where a player negotiates with another player.
 */
public class Diplomacy implements Order{

    /**
     * Represents the source player associated with an action or order.
     */
    Player d_sourcePlayer;

    /**
     * Represents the target player associated with an action or order.
     */
    Player d_targetPlayer;

    /**
     * Represents the ID of the player in the player order.
     */
    int d_playerOrderID;

    /**
     * Represents the name of the player in the player order.
     */
    String d_playerOrderName;

    /**
     * Constructor to create a Diplomacy object.
     *
     * @param p_sourcePlayer The player initiating the diplomacy
     * @param p_targetPlayer The player being negotiated with
     * @param p_playerOrderID The ID of the order
     * @param p_playerOrderName The name of the order
     */
    public Diplomacy(Player p_sourcePlayer, Player p_targetPlayer, int p_playerOrderID, String p_playerOrderName ) {
        this.d_sourcePlayer = p_sourcePlayer;
        this.d_targetPlayer = p_targetPlayer;
        this.d_playerOrderID = p_playerOrderID;
        this.d_playerOrderName = p_playerOrderName;
    }

    /**
     * Validates the diplomacy command.
     *
     * @return True since diplomacy orders do not require validation
     */
    @Override
    public boolean validateCommand(){

        if(d_sourcePlayer.getPlayerId()==d_targetPlayer.getPlayerId()){
            this.d_sourcePlayer.getRenderer().renderError("You cannot negiotiate with yourself");

            return false;
        }
        return true;
    }

    /**
     * Executes the diplomacy order by adding both players to each other's list of negotiated players.
     */
    @Override
    public void execute(){
        d_sourcePlayer.addToListOfNegotiatedPlayers(d_targetPlayer);
        d_targetPlayer.addToListOfNegotiatedPlayers(d_sourcePlayer);
        this.d_sourcePlayer.getRenderer().renderMessage("Negotiation Successful "+ "Between: " +this.d_sourcePlayer.getName()+ " And " + this.d_targetPlayer.getName());

        return;
    }
}
