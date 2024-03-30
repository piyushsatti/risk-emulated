package models.orders;

import controller.GameEngine;
import controller.MapInterface;
import controller.middleware.commands.StartupCommands;
import controller.statepattern.gameplay.Reinforcement;
import helpers.exceptions.*;
import models.Player;
import models.worldmap.Country;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * The BombTest class contains unit tests for the Bomb class.
 */
public class BombTest {

    /**
     * Tests the validateCommand method of the Bomb class when the command is valid.
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
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge, "test_map.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();

        // Validate command
        Bomb b = new Bomb(ge.d_players.get(0), ge.d_players.get(1), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getName(), ge.d_players.get(1).getAssignedCountries().get(0), ge);
        assertTrue(b.validateCommand());
    }

    /**
     * Tests the validateCommand method of the Bomb class when the command is invalid.
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
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge, "usa9.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();

        // Validate command
        Bomb b = new Bomb(ge.d_players.get(0), ge.d_players.get(1), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getName(), ge.d_players.get(0).getAssignedCountries().get(1), ge);
        assertFalse(b.validateCommand());
    }

    /**
     * Tests the execute method of the Bomb class for successfully executing the order.
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
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge, "usa9.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();
        Deploy deploy = new Deploy(ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), 4, ge);
        deploy.execute();

        // Execute Bomb
        Country c = ge.d_worldmap.getCountry(ge.d_players.get(0).getAssignedCountries().get(0));
        Bomb b = new Bomb(ge.d_players.get(1), ge.d_players.get(0), ge.d_players.get(1).getPlayerId(), ge.d_players.get(1).getName(), ge.d_players.get(0).getAssignedCountries().get(0), ge);
        b.execute();

        assertEquals(2, c.getReinforcements());
    }

    /**
     * Tests the execute method of the Bomb class for not executing the order when the conditions are not met.
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
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge, "usa9.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();
        Deploy deploy = new Deploy(ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), 15, ge);
        deploy.execute();

        // Execute Bomb
        Country c = ge.d_worldmap.getCountry(ge.d_players.get(0).getAssignedCountries().get(0));
        Bomb b = new Bomb(ge.d_players.get(1), ge.d_players.get(0), ge.d_players.get(1).getPlayerId(), ge.d_players.get(1).getName(), ge.d_players.get(0).getAssignedCountries().get(0), ge);
        b.execute();

        assertNotEquals(10, c.getReinforcements());
    }
}
