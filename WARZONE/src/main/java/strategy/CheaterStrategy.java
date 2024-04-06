package strategy;

import controller.GameEngine;
import models.Player;
import models.orders.Deploy;
import models.orders.Order;
import models.worldmap.Country;
import view.TerminalRenderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * CheaterStrategy class implements the Strategy interface for the cheater strategy.
 */
public class CheaterStrategy implements Strategy {

    /**
     * The player associated with this strategy.
     */
    private Player d_player;

    /**
     * The game engine associated with this strategy.
     */
    private GameEngine d_gameEngine;


    private String d_strategyName;

    /**
     * Constructor for CheaterStrategy.
     *
     * @param p_player     The player using this strategy.
     * @param p_gameEngine The game engine in which the player is playing.
     */
    public CheaterStrategy(Player p_player, GameEngine p_gameEngine) {
        this.d_player = p_player;
        this.d_gameEngine = p_gameEngine;
        this.d_strategyName = "Cheater";
    }

    public String getStrategyName() {
        return d_strategyName;
    }

    public void setStrategyName(String d_strategyName) {
        this.d_strategyName = d_strategyName;
    }

    /**
     * Returns the source country to be used for cheating.
     *
     * @return The source country for cheating.
     */
    public int getSourceCountry() {
        return this.d_player.getAssignedCountries().get(0);
    }

    /**
     * Returns the target country for cheating.
     *
     * @param p_sourceCountryId The ID of the source country.
     * @return The ID of the target country.
     */
    @Override
    public int getTargetCountry(int p_sourceCountryId) {
        return 0;
    }

    /**
     * Retrieves neighboring countries of a given country that are not owned by the player.
     *
     * @param p_sourceCountry The ID of the source country.
     * @return An ArrayList containing the IDs of neighboring countries not owned by the player.
     */
    public ArrayList<Integer> getNeighbouringCountry(int p_sourceCountry) {

        ArrayList<Integer> l_listOfAllBorderCountriesIDs = new ArrayList<>();
        for (Integer id : d_gameEngine.d_worldmap.getCountry(p_sourceCountry).getAllBorderCountriesIDs()) {
            if (!this.d_player.getAssignedCountries().contains(id)) {
                l_listOfAllBorderCountriesIDs.add(id);
            }
        }
        return l_listOfAllBorderCountriesIDs;
    }

    /**
     * Creates a conquer order for the player.
     *
     * @return The conquer order.
     */
    public Order createOrder() {

        ArrayList<Integer> assignedCountriesCopy = new ArrayList<>(this.d_player.getAssignedCountries());

        for (int l_sourceCountry : assignedCountriesCopy) {
            ArrayList<Integer> l_neighbourCountries = getNeighbouringCountry(l_sourceCountry);
            for (int l_countryId : l_neighbourCountries) {
                boolean l_isNeutral = true;

                for (Player l_player : this.d_gameEngine.d_players) {
                    if (l_player.getAssignedCountries().contains(l_countryId)) {
                        l_isNeutral = false;
                        l_player.removeAssignedCountries(l_countryId); // Collect players needing country removal
                        int l_armies = this.d_gameEngine.d_worldmap.getCountry(l_countryId).getReinforcements();
                        this.d_gameEngine.d_worldmap.getCountry(l_countryId).setReinforcements(l_armies * 2);
                        this.d_player.setAssignedCountries(l_countryId);
                        this.d_gameEngine.d_renderer.renderMessage("Cheater occupied: " + this.d_gameEngine.d_worldmap.getCountry(l_countryId).getCountryName());
                        break;
                    }
                }

                if (l_isNeutral) {
                    this.d_player.setAssignedCountries(l_countryId);
                    int l_armies = this.d_gameEngine.d_worldmap.getCountry(l_countryId).getReinforcements();
                    this.d_gameEngine.d_worldmap.getCountry(l_countryId).setReinforcements(l_armies * 2);
                    this.d_gameEngine.d_renderer.renderMessage("Cheater occupied: " + this.d_gameEngine.d_worldmap.getCountry(l_countryId).getCountryName());
                }
            }
        }
        return null;
    }
}
