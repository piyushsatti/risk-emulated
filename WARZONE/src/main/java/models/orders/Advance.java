package models.orders;

import controller.GameEngine;
import models.Player;
import view.TerminalRenderer;

/**
 * Represents an "Advance" order, which allows a player to move troops from one country to another.
 */
public class Advance implements Order {
    /**
     * The ID of the country from which the action is performed.
     */
    private final int d_fromCountryID;
    /**
     * The ID of the country to which the action is directed.
     */
    private final int d_toCountryID;
    /**
     * The number of troops involved in the action.
     */
    private final int d_advancingtroops;
    /**
     * The player who issued the order.
     */
    private Player d_sourcePlayer;
    /**
     * The player who is the target of the order.
     */
    private Player d_targetPlayer;
    /**
     * The unique identifier for the player order.
     */
    private int d_playerOrderID;
    /**
     * The name of the player order.
     */
    private String d_playerOrderName;
    /**
     * The game engine associated with the order.
     */
    private GameEngine d_gameEngine;

    /**
     * The terminal renderer used for displaying messages.
     */
    private TerminalRenderer d_terminalRenderer;


    /**
     * Constructs a new Advance order with the specified parameters.
     *
     * @param p_sourcePlayer    The player initiating the advance order.
     * @param p_targetPlayer    The player owning the target country.
     * @param p_playerOrderID   ID of the player issuing the order.
     * @param p_playerOrderName Name of the player issuing the order.
     * @param p_fromCountryID   ID of the country from which troops are advancing.
     * @param p_toCountryID     ID of the country to which troops are advancing.
     * @param p_advancingtroops Number of troops being advanced.
     * @param p_gameEngine      The game engine managing the game state.
     */
    public Advance(Player p_sourcePlayer, Player p_targetPlayer,String p_playerOrderName, int p_playerOrderID, int p_fromCountryID, int p_toCountryID, int p_advancingtroops, GameEngine p_gameEngine) {
        this.d_sourcePlayer = p_sourcePlayer;
        this.d_targetPlayer = p_targetPlayer;
        this.d_playerOrderName = p_playerOrderName;
        this.d_playerOrderID = p_playerOrderID;
        this.d_fromCountryID = p_fromCountryID;
        this.d_toCountryID = p_toCountryID;
        this.d_advancingtroops = p_advancingtroops;
        this.d_gameEngine = p_gameEngine;
        this.d_terminalRenderer = new TerminalRenderer(this.d_gameEngine);

    }

    /**
     * Validates the "Advance" command to ensure it meets the necessary conditions for execution.
     *
     * @return true if the command is valid, false otherwise.
     */
    @Override
    public boolean validateCommand(){

        if (!d_sourcePlayer.getAssignedCountries().contains(d_fromCountryID)) {
            System.out.println("Player does not own the source country");
            return false;
        }
        if (!this.d_gameEngine.d_worldmap.getCountry(this.d_fromCountryID).getBorderCountries().containsKey(d_toCountryID)) {
            System.out.println("Cannot move to a non neighbouring country.");
            return false;
        }

       int l_currentReinforcementsFromCountry =  this.d_gameEngine.d_worldmap.getCountry(this.d_fromCountryID).getReinforcements();
        if( this.d_advancingtroops < 0){
            System.out.println("Not enough troops to advance.");
            return false;
        }

        return true;
    }

    /**
     * Executes the "Advance" order.
     * <p>
     * If the source player owns the source country and the target country is a neighboring country, the troops are advanced.
     * If the target country is owned by the source player, troops are moved between own adjacent territories.
     * If the target country is owned by another player, it results in an attack.
     * </p>
     * <p>
     * If the attack succeeds, the defending player loses control of the target country and the attacking player gains control,
     * and a card is awarded to the attacking player.
     * If the attack fails, the defending player retains control of the target country.
     * </p>
     */
    @Override
    public void execute() {

        if (this.d_gameEngine.d_worldmap.getCountry(this.d_fromCountryID).getReinforcements() - this.d_advancingtroops >= 0) {
            if (d_sourcePlayer.getAssignedCountries().contains(d_toCountryID)) {

                //Move order moving from within own adjacent territories.
                int l_currentReinforcementsFromCountry =  this.d_gameEngine.d_worldmap.getCountry(this.d_fromCountryID).getReinforcements();
                this.d_gameEngine.d_worldmap.getCountry(this.d_fromCountryID).setReinforcements(l_currentReinforcementsFromCountry - this.d_advancingtroops);
                int l_currentReinforcementsToCountry =  this.d_gameEngine.d_worldmap.getCountry(this.d_toCountryID).getReinforcements();
                this.d_gameEngine.d_worldmap.getCountry(this.d_toCountryID).setReinforcements(l_currentReinforcementsToCountry + this.d_advancingtroops);
                System.out.println("Troops advanced succesfully ");
                return;

            } else {
                // Attacking adjacent territory
                if(d_sourcePlayer.getListOfNegotiatedPlayers().contains(d_targetPlayer)){
                    System.out.println(d_sourcePlayer.getName()+" has negotiated with "+d_targetPlayer.getName());
                    //skip execute
                    return;
                }
                int l_currentReinforcementsToCountry =  this.d_gameEngine.d_worldmap.getCountry(this.d_toCountryID).getReinforcements();
                int l_currentReinforcementsFromCountry =  this.d_gameEngine.d_worldmap.getCountry(this.d_fromCountryID).getReinforcements();
                int attackingArmiesKills = (this.d_advancingtroops) * 60 / 100;

                int defendingArmiesKills = (l_currentReinforcementsToCountry) * 70 / 100;

                int attackingarmiessurvived = this.d_advancingtroops - defendingArmiesKills;
                int defendingarmiessurvived = l_currentReinforcementsToCountry - attackingArmiesKills;
                if (defendingarmiessurvived <= 0) {
                    // Attacker won
                    d_sourcePlayer.setAssignedCountries(this.d_toCountryID);
                    d_targetPlayer.removeAssignedCountries(this.d_toCountryID);
                    this.d_gameEngine.d_worldmap.getCountry(this.d_toCountryID).setReinforcements(attackingarmiessurvived);
                    this.d_gameEngine.d_worldmap.getCountry(this.d_fromCountryID).setReinforcements(l_currentReinforcementsFromCountry - this.d_advancingtroops);
                    d_sourcePlayer.addCard();
                    System.out.println("Advance attack succesfull");
                    return;
                } else {
                    //Defender won
                    this.d_gameEngine.d_worldmap.getCountry(this.d_toCountryID).setReinforcements(defendingarmiessurvived);
                    if (attackingarmiessurvived > 0) {
                        this.d_gameEngine.d_worldmap.getCountry(this.d_fromCountryID).setReinforcements(l_currentReinforcementsFromCountry - this.d_advancingtroops + attackingarmiessurvived);
                    } else {
                        this.d_gameEngine.d_worldmap.getCountry(this.d_fromCountryID).setReinforcements(l_currentReinforcementsFromCountry - this.d_advancingtroops);
                    }
                    System.out.println("Defence was succesfull");

                    return;

                }

            }

        } else {
            System.out.println("Not enough troops to advance");
        }
    }
}
