package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.MapFileManagement.MapInterface;
import controller.middleware.commands.StartupCommands;
import helpers.exceptions.ContinentAlreadyExistsException;
import helpers.exceptions.ContinentDoesNotExistException;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.DuplicateCountryException;
import models.Player;
import models.worldmap.Continent;
import models.worldmap.Country;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Test class for the Reinforcement phase.
 */
public class ReinforcementTest {
    /**
     * Represents an instance of the game engine responsible for managing game logic and state.
     */
    GameEngine d_ge = new GameEngine();

    /**
     * Represents an instance of startup commands with a specific command type.
     */
    StartupCommands d_cmd = new StartupCommands("assigncountries");

    /**
     * Represents an instance of the reinforcement phase associated with a game engine.
     */
    Reinforcement d_rf = new Reinforcement(d_ge);

    /**
     * Test for the run method in Reinforcement phase.
     *
     * @throws CountryDoesNotExistException    when a country does not exist
     * @throws ContinentAlreadyExistsException when a continent already exists
     * @throws ContinentDoesNotExistException  when a continent does not exist
     * @throws DuplicateCountryException       when a duplicate country is encountered
     * @throws FileNotFoundException           when a file is not found
     */
    @Test
    public void runTest1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
        MapInterface l_mp = new MapInterface();
        d_ge.d_worldmap = l_mp.loadMap(d_ge, "usa9.map");
        d_ge.d_players.add(new Player("Shashi", d_ge));

        d_rf.run();


        int l_bonus = 0;
        HashMap<Integer, Continent> l_continents = d_ge.d_worldmap.getContinents();
        for (Continent l_continent : l_continents.values()) {
            boolean l_allCountriesFound = true; // Flag to track if all countries of the continent are found
            for (Country l_country : d_ge.d_worldmap.getContinentCountries(l_continent).values()) {
                if (!d_ge.d_players.get(0).getAssignedCountries().contains(l_country.getCountryID())) {
                    l_allCountriesFound = false; // If any country is not found, set flag to false
                    break;
                }
            }
            if (l_allCountriesFound) {
                l_bonus += l_continent.getBonus();
            }
        }


        int l_numberOfTroops = Math.max(d_ge.d_players.get(0).getAssignedCountries().size() / 3 + l_bonus, 3);

        assertEquals(l_numberOfTroops, d_ge.d_players.get(0).getReinforcements());

    }

    /**
     * Test for the run method in Reinforcement phase with multiple players.
     *
     * @throws CountryDoesNotExistException    when a country does not exist
     * @throws ContinentAlreadyExistsException when a continent already exists
     * @throws ContinentDoesNotExistException  when a continent does not exist
     * @throws DuplicateCountryException       when a duplicate country is encountered
     * @throws FileNotFoundException           when a file is not found
     */
    @Test
    public void runTest2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
        MapInterface l_mp = new MapInterface();
        d_ge.d_worldmap = l_mp.loadMap(d_ge, "usa9.map");
        d_ge.d_players.add(new Player("Shashi", d_ge));
        d_ge.d_players.add(new Player("Priyanshu", d_ge));

        d_rf.run();

        //main logic: armies should return the value of l_numberOfTroops
        for (Player l_player : d_ge.d_players) {
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
            assertEquals(l_numberOfTroops, l_player.getReinforcements());


        }
    }
}