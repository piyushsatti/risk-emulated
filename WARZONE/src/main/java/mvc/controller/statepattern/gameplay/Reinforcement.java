package mvc.controller.statepattern.gameplay;

import mvc.controller.GameEngine;
import mvc.controller.statepattern.Phase;
import mvc.models.Player;
import mvc.models.worldmap.Continent;
import mvc.models.worldmap.Country;

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

    /**
     * This method is intended to display the game menu.
     */
    @Override
    public void displayMenu() {

    }

    /**
     * This method is intended to advance the game to the next step or phase.
     */
    @Override
    public void next() {

    }

    /**
     * This method is intended to end the game.
     */
    @Override
    public void endGame() {

    }

    /**
     * Resets the "finished issue order" status for all players.
     * This status typically indicates whether a player has finished issuing orders for the current turn.
     */
    public void allPlayersReset() {
        for (Player p : d_ge.d_players) {
            if (p.isFinishedIssueOrder()) {
                p.setFinishedIssueOrder(false);
            }
        }
    }

    /**
     * Executes the reinforcement phase.
     */
    @Override
    public void run() {
        allPlayersReset();

        assignReinforcements(d_ge.d_players);
        d_ge.setCurrentState(new IssueOrder(d_ge));
    }

    /**
     * Assigns reinforcements to each player based on their assigned countries and controlled continents.
     * This method calculates the number of reinforcements each player receives by considering the number of assigned countries
     * and the continents they control. It then sets the calculated number of reinforcements for each player.
     *
     * @param players The list of players for whom reinforcements are to be assigned.
     */
    public void assignReinforcements(ArrayList<Player> players) {
        for (Player l_player : players) {
            l_player.getListOfNegotiatedPlayers().clear();
            int l_bonus = 0;
            HashMap<Integer, Continent> l_continents = d_ge.d_worldmap.getContinents();
            for (Continent l_continent : l_continents.values()) {
                boolean l_allCountriesFound = true; // Flag to track if all countries of the continent are found
                for (Country l_country : d_ge.d_worldmap.getContinentCountries(l_continent).values()) {
                    if (!l_player.getAssignedCountries().contains(l_country.getCountryID())) {
                        l_allCountriesFound = false; // If any country is not found, set flag to false
                        break;
                    }
                }
                if (l_allCountriesFound) {
                    // All countries of the continent are present
                    l_bonus += l_continent.getBonus(); // Get the bonus value
                }
            }
            int l_numberOfTroops = Math.max(l_player.getAssignedCountries().size() / 3 + l_bonus, 3);
            l_player.setReinforcements(l_numberOfTroops);
        }
        this.d_ge.d_renderer.renderMessage("----------------------------------------------------");
        this.d_ge.d_renderer.renderMessage("Reinforcements Done. Going to Issue Orders");
        this.d_ge.d_renderer.renderMessage("----------------------------------------------------");
    }
}