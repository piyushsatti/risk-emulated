package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.statepattern.Phase;
import models.Player;
import models.worldmap.Continent;
import models.worldmap.Country;

import java.util.HashMap;

public class Reinforcement extends Phase {
    public Reinforcement(GameEngine g) {
        super(g);
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

    @Override
    public void run() {
        for (Player player : d_ge.d_players) {
            player.getListOfNegotiatedPlayers().clear();
            int bonus = 0;
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
        d_ge.setCurrentState(new IssueOrder(d_ge));
    }
}
