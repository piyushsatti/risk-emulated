package models.orders;

import controller.GameEngine;
import controller.MapFileManagement.MapInterface;
import controller.middleware.commands.StartupCommands;
import controller.statepattern.gameplay.Reinforcement;
import controller.statepattern.gameplay.Startup;
import helpers.exceptions.*;
import models.Player;
import models.worldmap.Country;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * The BlockadeTest class contains unit tests for the Blockade class.
 */
public class BlockadeTest {

    /**
     * Tests the validateCommand method of the Blockade class when the command is valid.
     *
     * @throws CountryDoesNotExistException     If a country does not exist.
     * @throws ContinentAlreadyExistsException If a continent already exists.
     * @throws ContinentDoesNotExistException  If a continent does not exist.
     * @throws DuplicateCountryException       If a country is duplicated.
     * @throws FileNotFoundException           If a file is not found.
     */
    @Test
    public void validateCommandTests1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
        // Test setup
        MapInterface mp = new MapInterface();
        GameEngine ge = new GameEngine();
        ge.d_worldmap = mp.loadMap(ge, "usa9.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));
        ge.setCurrentState(new Startup(ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();

        // Validate command
        Blockade bl = new Blockade(ge.d_players.get(0), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getName(), ge.d_players.get(0).getAssignedCountries().get(1), ge);
        assertTrue(bl.validateCommand());
    }

    /**
     * Tests the validateCommand method of the Blockade class when the command is invalid.
     *
     * @throws CountryDoesNotExistException     If a country does not exist.
     * @throws ContinentAlreadyExistsException If a continent already exists.
     * @throws ContinentDoesNotExistException  If a continent does not exist.
     * @throws DuplicateCountryException       If a country is duplicated.
     * @throws FileNotFoundException           If a file is not found.
     */
    @Test
    public void validateCommandTests2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
        // Test setup
        MapInterface mp = new MapInterface();
        GameEngine ge = new GameEngine();
        ge.d_worldmap = mp.loadMap(ge, "usa9.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));
        ge.setCurrentState(new Startup(ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();

        // Validate command
        Blockade bl = new Blockade(ge.d_players.get(0), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getName(), ge.d_players.get(1).getAssignedCountries().get(0), ge);
        assertFalse(bl.validateCommand());
    }

    /**
     * Tests the execute method of the Blockade class for successfully executing the order.
     *
     * @throws CountryDoesNotExistException     If a country does not exist.
     * @throws ContinentAlreadyExistsException If a continent already exists.
     * @throws ContinentDoesNotExistException  If a continent does not exist.
     * @throws DuplicateCountryException       If a country is duplicated.
     * @throws FileNotFoundException           If a file is not found.
     * @throws InvalidCommandException         If the command is invalid.
     */
    @Test
    public void orderValidationTest1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        // Test setup
        MapInterface mp = new MapInterface();
        GameEngine ge = new GameEngine();
        ge.d_worldmap = mp.loadMap(ge, "usa9.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));
        ge.setCurrentState(new Startup(ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();
        Deploy deploy = new Deploy(ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), 5, ge);
        deploy.execute();

        // Execute Blockade
        Country c = ge.d_worldmap.getCountry(ge.d_players.get(0).getAssignedCountries().get(0));
        Blockade bl = new Blockade(ge.d_players.get(0), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getName(), ge.d_players.get(0).getAssignedCountries().get(0), ge);
        bl.execute();

        // Assertion
        assertEquals(15, c.getReinforcements());
    }

    /**
     * Tests the execute method of the Blockade class for not executing the order when the conditions are not met.
     *
     * @throws CountryDoesNotExistException     If a country does not exist.
     * @throws ContinentAlreadyExistsException If a continent already exists.
     * @throws ContinentDoesNotExistException  If a continent does not exist.
     * @throws DuplicateCountryException       If a country is duplicated.
     * @throws FileNotFoundException           If a file is not found.
     * @throws InvalidCommandException         If the command is invalid.
     */
    @Test
    public void orderValidationTest2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        // Test setup
        MapInterface mp = new MapInterface();
        GameEngine ge = new GameEngine();
        ge.d_worldmap = mp.loadMap(ge, "usa9.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));
        ge.setCurrentState(new Startup(ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();
        Deploy deploy = new Deploy(ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), 12, ge);
        deploy.execute();

        // Execute Blockade
        Country c = ge.d_worldmap.getCountry(ge.d_players.get(0).getAssignedCountries().get(0));
        Blockade bl = new Blockade(ge.d_players.get(0), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getName(), ge.d_players.get(0).getAssignedCountries().get(0), ge);
        bl.execute();

        // Assertion
        assertEquals(36, c.getReinforcements());
    }

    /**
     * Tests the execute method of the Blockade class for not executing the order when the conditions are not met.
     *
     * @throws CountryDoesNotExistException     If a country does not exist.
     * @throws ContinentAlreadyExistsException If a continent already exists.
     * @throws ContinentDoesNotExistException  If a continent does not exist.
     * @throws DuplicateCountryException       If a country is duplicated.
     * @throws FileNotFoundException           If a file is not found.
     * @throws InvalidCommandException         If the command is invalid.
     */
    @Test
    public void orderValidationTest3() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        // Test setup
        MapInterface mp = new MapInterface();
        GameEngine ge = new GameEngine();
        ge.d_worldmap = mp.loadMap(ge, "usa9.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));
        ge.setCurrentState(new Startup(ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();
        Deploy deploy = new Deploy(ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), 4, ge);
        deploy.execute();

        // Execute Blockade
        Country c = ge.d_worldmap.getCountry(ge.d_players.get(0).getAssignedCountries().get(0));
        Blockade bl = new Blockade(ge.d_players.get(0), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getName(), ge.d_players.get(0).getAssignedCountries().get(0), ge);
        bl.execute();

        // Assertion
        assertNotEquals(3, c.getReinforcements());
    }

}
