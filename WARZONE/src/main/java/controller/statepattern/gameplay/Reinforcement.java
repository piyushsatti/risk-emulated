package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.statepattern.Phase;
import models.Player;
import models.worldmap.Continent;
import models.worldmap.Country;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Represents the phase of reinforcement.
 */
public class Reinforcement extends Phase {
    /**
     * Constructor for Reinforcement.
     *
     * @param p_gameEngine The GameEngine object.
     */
    public Reinforcement(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void displayMenu() {

    }

    @Override
    public void next() {

    }

    @Override
    public void endGame() {

    }
    public void allPlayersResent(){
        for(Player p : d_ge.d_players){
            if(p.isFinishedIssueOrder()){
                p.setFinishedIssueOrder(false);
            }
        }
    }

    /**
     * Executes the reinforcement phase.
     */
    @Override
    public void run() {
        allPlayersResent();
        assignReinforcements(d_ge.d_players);
        d_ge.setCurrentState(new IssueOrder(d_ge));
    }
    public void assignReinforcements(ArrayList<Player> players)
    {
        for(Player player : players){
            player.getListOfNegotiatedPlayers().clear();
            int bonus =0;
            HashMap<Integer, Continent> continents = d_ge.d_worldmap.getContinents();
            for (Continent continent : continents.values()) {
                boolean allCountriesFound = true; // Flag to track if all countries of the continent are found
                for (Country country : d_ge.d_worldmap.getContinentCountries(continent).values()) {
                    if (!player.getAssignedCountries().contains(country.getCountryID())) {
                        allCountriesFound = false; // If any country is not found, set flag to false
                        break;
                    }
                }
                if (allCountriesFound) {
                    // All countries of the continent are present
                    bonus+= continent.getBonus(); // Get the bonus value
                }
            }
            int l_numberOfTroops = Math.max(player.getAssignedCountries().size() / 3 +bonus, 3);
            player.setReinforcements(l_numberOfTroops);
        }
        this.d_ge.d_renderer.renderMessage("Reinforcements Done. Going to Issue Orders");
        this.d_ge.d_renderer.renderMessage("----------------------------------------------------");
    }
}
