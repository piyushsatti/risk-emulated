package models.orders;

import controller.GameEngine;
import models.Player;

public class Blockade implements Order{
    Player d_sourcePlayer;

    int d_playerOrderID;

    String d_playerOrderName;

    private final int d_blockadeCountryID;

    GameEngine d_gameEngine;

    @Override
    public boolean validateCommand(){

        return true;
    }

    public Blockade(Player p_sourcePlayer, int p_playerOrderID, String p_playerOrderName, int p_blockadeCountryID, GameEngine p_gameEngine) {
        this.d_sourcePlayer = p_sourcePlayer;
        this.d_playerOrderID = p_playerOrderID;
        this.d_playerOrderName  = p_playerOrderName;
        this.d_blockadeCountryID = p_blockadeCountryID;
        this.d_gameEngine = p_gameEngine;
    }


    @Override
    public void execute(){
        if (!d_sourcePlayer.getAssignedCountries().contains(d_blockadeCountryID)) {
            System.out.println("Player does not own the source country");
            return;
        }
        int l_currentReinforcementsBlockadeCountry = this.d_gameEngine.CURRENT_MAP.getCountry(this.d_blockadeCountryID).getReinforcements();
        this.d_gameEngine.CURRENT_MAP.getCountry(this.d_blockadeCountryID).setReinforcements(l_currentReinforcementsBlockadeCountry*3);
        this.d_sourcePlayer.removeAssignedCountries(this.d_blockadeCountryID);

    }

}
