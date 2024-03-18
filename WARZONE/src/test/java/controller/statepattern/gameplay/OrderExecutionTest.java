package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.MapInterface;
import controller.middleware.commands.StartupCommands;
import helpers.exceptions.ContinentAlreadyExistsException;
import helpers.exceptions.ContinentDoesNotExistException;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.DuplicateCountryException;
import models.Player;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for OrderExecution phase.
 */
public class OrderExecutionTest {
    /**
     * Tests for the Winner method in the OrderExecution class.
     * Checks if the method correctly identifies a winner when only one player exists.
     *
     * @throws CountryDoesNotExistException     If the country does not exist.
     * @throws ContinentAlreadyExistsException  If the continent already exists.
     * @throws ContinentDoesNotExistException   If the continent does not exist.
     * @throws DuplicateCountryException        If a duplicate country is encountered.
     * @throws FileNotFoundException           If the specified file is not found.
     */
    @Test
    public void isWinnerTest1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge, "usa9.map");
        ge.d_players.add(new Player("Shashi",ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        OrderExecution oe = new OrderExecution(ge);
        cmd.execute(ge);
        assertTrue(oe.isWinner());
    }
    /**
     * Tests for the Winner method in the OrderExecution class.
     * Checks if the method correctly identifies no winner when multiple players exist.
     *
     * @throws CountryDoesNotExistException     If the country does not exist.
     * @throws ContinentAlreadyExistsException  If the continent already exists.
     * @throws ContinentDoesNotExistException   If the continent does not exist.
     * @throws DuplicateCountryException        If a duplicate country is encountered.
     * @throws FileNotFoundException           If the specified file is not found.
     */
    @Test
    public void isWinnerTest2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge, "usa9.map");
        ge.d_players.add(new Player("Shashi",ge));
        ge.d_players.add(new Player("Priyanshu",ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        OrderExecution oe = new OrderExecution(ge);
        cmd.execute(ge);
        assertFalse(oe.isWinner());
    }
}