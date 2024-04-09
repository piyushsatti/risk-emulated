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
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Test class for the Reinforcement phase.
 */
public class ReinforcementTest {
    /**
     * Represents an instance of the game engine responsible for managing game logic and state.
     */
    GameEngine ge = new GameEngine();




    /**
     * Represents an instance of startup commands with a specific command type.
     */
    StartupCommands cmd = new StartupCommands("assigncountries");

    /**
     * Represents an instance of the reinforcement phase associated with a game engine.
     */
    Reinforcement rf = new Reinforcement(ge);

    @Before
    public void run()
    {ge.setCurrentState(new Startup(ge));
    }

    /**
     * Test for the run method in Reinforcement phase.
     * @throws CountryDoesNotExistException when a country does not exist
     * @throws ContinentAlreadyExistsException when a continent already exists
     * @throws ContinentDoesNotExistException when a continent does not exist
     * @throws DuplicateCountryException when a duplicate country is encountered
     * @throws FileNotFoundException when a file is not found
     */
    @Test
    public void runTest1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {

        MapInterface mp = new MapInterface();
        ge.d_worldmap = mp.loadMap(ge, "usa9.map");
        ge.d_players.add(new Player("Shashi",ge));
        cmd.execute(ge);

        rf.run();


        int bonus = 0;
        HashMap<Integer, Continent> continents = ge.d_worldmap.getContinents();
        for (Continent continent : continents.values()) {
            boolean allCountriesFound = true; // Flag to track if all countries of the continent are found
            for (Country country : ge.d_worldmap.getContinentCountries(continent).values()) {
                if (!ge.d_players.get(0).getAssignedCountries().contains(country.getCountryID())) {
                    allCountriesFound = false; // If any country is not found, set flag to false
                    break;
                }
            }
            if (allCountriesFound) {
                bonus += continent.getBonus();
            }
        }


        int l_numberOfTroops = Math.max(ge.d_players.get(0).getAssignedCountries().size() / 3 + bonus, 3);

        assertEquals(l_numberOfTroops, ge.d_players.get(0).getReinforcements());

    }

    /**
     * Test for the run method in Reinforcement phase with multiple players.
     * @throws CountryDoesNotExistException when a country does not exist
     * @throws ContinentAlreadyExistsException when a continent already exists
     * @throws ContinentDoesNotExistException when a continent does not exist
     * @throws DuplicateCountryException when a duplicate country is encountered
     * @throws FileNotFoundException when a file is not found
     */
    @Test
    public void runTest2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
        MapInterface mp = new MapInterface();
        ge.d_worldmap = mp.loadMap(ge, "usa9.map");
        ge.d_players.add(new Player("Shashi", ge));
        ge.d_players.add(new Player("Priyanshu",ge));
        cmd.execute(ge);

        rf.run();

        //main logic: armies should return the value of l_numberOfTroops
        for (Player player : ge.d_players) {
            int bonus = 0;
            HashMap<Integer, Continent> continents = ge.d_worldmap.getContinents();
            for (Continent continent : continents.values()) {
                boolean allCountriesFound = true; // Flag to track if all countries of the continent are found
                for (Country country : ge.d_worldmap.getContinentCountries(continent).values()) {
                    if (!player.getAssignedCountries().contains(country.getCountryID())) {
                        allCountriesFound = false; // If any country is not found, set flag to false
                        break;
                    }
                }
                if (allCountriesFound) {
                    // All countries of the continent are present
                    bonus += continent.getBonus(); // Get the bonus value
                }
            }
            int l_numberOfTroops = Math.max(player.getAssignedCountries().size() / 3 + bonus, 3);
            assertEquals(l_numberOfTroops, player.getReinforcements());


        }
    }
}