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
 * The AdvanceTest class contains unit tests for the Advance class.
 */
public class AdvanceTest {

    /**
     * Tests the validateCommand method of the Advance class when the command is valid.
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
        MapInterface.loadMap2(ge, "order_test.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();

        // Validate command
        Advance av = new Advance(ge.d_players.get(0), ge.d_players.get(1), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), ge.d_players.get(1).getAssignedCountries().get(0), 5, ge);
        assertTrue(av.validateCommand());
    }

    /**
     * Tests the validateCommand method of the Advance class when the command is invalid.
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
        MapInterface.loadMap2(ge, "order_test.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();

        // Validate command
        Advance av = new Advance(ge.d_players.get(0), ge.d_players.get(1), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), ge.d_players.get(0).getAssignedCountries().get(0), 5, ge);
        assertFalse(av.validateCommand());
    }

    /**
     * Tests the execute method of the Advance class for successfully executing the order.
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
        Deploy deploy = new Deploy(ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), 5, ge);
        deploy.execute();

        // Execute Advance
        Advance av = new Advance(ge.d_players.get(0), ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), ge.d_players.get(0).getAssignedCountries().get(1), 5, ge);
        av.execute();

        Country c = ge.d_worldmap.getCountry(ge.d_players.get(0).getAssignedCountries().get(1));
        assertEquals(5, c.getReinforcements());
    }

    /**
     * Tests the move result after executing Advance order.
     *
     * @throws CountryDoesNotExistException     If a country does not exist.
     * @throws ContinentAlreadyExistsException If a continent already exists.
     * @throws ContinentDoesNotExistException  If a continent does not exist.
     * @throws DuplicateCountryException       If a country is duplicated.
     * @throws FileNotFoundException           If a file is not found.
     * @throws InvalidCommandException         If the command is invalid.
     */
    @Test
    public void advanceMove() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        // Test setup
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge, "usa9.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();
        Deploy deploy = new Deploy(ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), 5, ge);
        deploy.execute();

        // Execute Advance
        Advance av = new Advance(ge.d_players.get(0), ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), ge.d_players.get(0).getAssignedCountries().get(1), 5, ge);
        av.execute();

        Country c = ge.d_worldmap.getCountry(ge.d_players.get(0).getAssignedCountries().get(1));
        assertEquals(5, c.getReinforcements());
    }

    /**
     * Tests the conquer result after executing Advance order.
     *
     * @throws CountryDoesNotExistException     If a country does not exist.
     * @throws ContinentAlreadyExistsException If a continent already exists.
     * @throws ContinentDoesNotExistException  If a continent does not exist.
     * @throws DuplicateCountryException       If a country is duplicated.
     * @throws FileNotFoundException           If a file is not found.
     * @throws InvalidCommandException         If the command is invalid.
     */
    @Test
    public void advanceConquerCheck() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        // Test setup
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge, "order_test.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();
        Deploy deploy = new Deploy(ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), 5, ge);
        deploy.execute();

        // Execute Advance
        Advance av = new Advance(ge.d_players.get(0), ge.d_players.get(1), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), ge.d_players.get(1).getAssignedCountries().get(0), 5, ge);
        av.execute();

        Country c = ge.d_worldmap.getCountry(ge.d_players.get(0).getAssignedCountries().get(1));
        assertEquals(5, c.getReinforcements());
    }
}
