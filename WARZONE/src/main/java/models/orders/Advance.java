package models.orders;

import controller.GameEngine;
import models.Player;
import view.TerminalRenderer;

public class Advance implements Order {

    Player d_sourcePlayer;
    Player d_targetPlayer;

    int d_playerOrderID;


    String d_playerOrderName;

    private final int d_fromCountryID;

    private final int d_toCountryID;

    private final int d_advancingtroops;

    GameEngine d_gameEngine;
    TerminalRenderer d_terminalRenderer;


    public Advance(Player p_sourcePlayer, String p_playerOrderName, int p_playerOrderID, int p_fromCountryID, int p_toCountryID, int p_advancingtroops, GameEngine p_gameEngine) {
        this.d_sourcePlayer = p_sourcePlayer;
        //this.d_targetPlayer = p_targetPlayer;
        this.d_playerOrderName = p_playerOrderName;

        this.d_playerOrderID = p_playerOrderID;

        this.d_fromCountryID = p_fromCountryID;

        this.d_toCountryID = p_toCountryID;

        this.d_advancingtroops = p_advancingtroops;

        this.d_gameEngine = p_gameEngine;

        this.d_terminalRenderer = new TerminalRenderer(this.d_gameEngine);

    }

   @Override
    public boolean validateCommand(){

        if (!d_sourcePlayer.getAssignedCountries().contains(d_fromCountryID)) {
            System.out.println("Player does not own the source country");
            return false;
        }
        if (!this.d_gameEngine.CURRENT_MAP.getCountry(this.d_fromCountryID).getBorderCountries().containsKey(d_toCountryID)) {
            System.out.println("Cannot move to a non neighbouring country.");
            return false;
        }

        return true;
    }

    @Override
    public void execute() {

        if (this.d_gameEngine.CURRENT_MAP.getCountry(this.d_fromCountryID).getReinforcements() - this.d_advancingtroops >= 1) {
            if (d_sourcePlayer.getAssignedCountries().contains(d_toCountryID)) {

                //Move order moving from within own adjacent territories.
                int l_currentReinforcementsFromCountry =  this.d_gameEngine.CURRENT_MAP.getCountry(this.d_fromCountryID).getReinforcements();

                this.d_gameEngine.CURRENT_MAP.getCountry(this.d_fromCountryID).setReinforcements(l_currentReinforcementsFromCountry - this.d_advancingtroops);


                int l_currentReinforcementsToCountry =  this.d_gameEngine.CURRENT_MAP.getCountry(this.d_toCountryID).getReinforcements();

                this.d_gameEngine.CURRENT_MAP.getCountry(this.d_toCountryID).setReinforcements(l_currentReinforcementsToCountry + this.d_advancingtroops);
                return;

            } else {
                // Attacking adjacent territory
                int l_currentReinforcementsToCountry =  this.d_gameEngine.CURRENT_MAP.getCountry(this.d_toCountryID).getReinforcements();
                int l_currentReinforcementsFromCountry =  this.d_gameEngine.CURRENT_MAP.getCountry(this.d_fromCountryID).getReinforcements();
                int attackingArmiesKills = (this.d_advancingtroops) * 60 / 100;

                int defendingArmiesKills = (l_currentReinforcementsToCountry) * 70 / 100;

                int attackingarmiessurvived = this.d_advancingtroops - defendingArmiesKills;
                int defendingarmiessurvived = l_currentReinforcementsToCountry - attackingArmiesKills;
                if (defendingarmiessurvived <= 0) {
                    // Attacker won
                    d_sourcePlayer.setAssignedCountries(this.d_toCountryID);
                    d_targetPlayer.removeAssignedCountries(this.d_toCountryID);
                    this.d_gameEngine.CURRENT_MAP.getCountry(this.d_toCountryID).setReinforcements(attackingarmiessurvived);
                    this.d_gameEngine.CURRENT_MAP.getCountry(this.d_fromCountryID).setReinforcements(l_currentReinforcementsFromCountry - this.d_advancingtroops);
                    d_sourcePlayer.addCard();
                } else {
                    //Defender won
                    this.d_gameEngine.CURRENT_MAP.getCountry(this.d_toCountryID).setReinforcements(defendingarmiessurvived);
                    if (attackingarmiessurvived > 0) {
                        this.d_gameEngine.CURRENT_MAP.getCountry(this.d_fromCountryID).setReinforcements(l_currentReinforcementsFromCountry - this.d_advancingtroops + attackingarmiessurvived);
                    } else {
                        this.d_gameEngine.CURRENT_MAP.getCountry(this.d_fromCountryID).setReinforcements(l_currentReinforcementsFromCountry - this.d_advancingtroops);
                    }
                    return;

                }


            }

        } else {
            System.out.println("Not enough troops to advance");
        }
    }
}
