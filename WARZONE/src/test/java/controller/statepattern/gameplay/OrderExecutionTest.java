package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.MapFileManagement.ConquestMapInterface;
import controller.MapFileManagement.MapAdapter;
import controller.MapFileManagement.MapFileLoader;
import controller.MapFileManagement.MapInterface;
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
     * @throws CountryDoesNotExistException    If the country does not exist.
     * @throws ContinentAlreadyExistsException If the continent already exists.
     * @throws ContinentDoesNotExistException  If the continent does not exist.
     * @throws DuplicateCountryException       If a duplicate country is encountered.
     * @throws FileNotFoundException           If the specified file is not found.
     */
    @Test
    public void isWinnerTest1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
<<<<<<< Updated upstream
        MapInterface l_mp = new MapInterface();
        GameEngine l_ge = new GameEngine();
        l_ge.d_worldmap = l_mp.loadMap(l_ge, "usa9.map");
        l_ge.d_players.add(new Player("Shashi", l_ge));
        l_ge.setCurrentState(new Startup(l_ge));
        StartupCommands l_cmd = new StartupCommands("assigncountries");
        OrderExecution l_oe = new OrderExecution(l_ge);
        l_cmd.execute(l_ge);
        assertTrue(l_oe.isWinner());
=======
        MapInterface mp = null;
        GameEngine ge = new GameEngine();
        MapFileLoader l_mfl = new MapFileLoader(ge, "usa9.map");

        if(l_mfl.isConquest()){
            mp = new MapAdapter(new ConquestMapInterface());
        }else{
            mp = new MapInterface();
        }
        ge.d_worldmap = mp.loadMap(ge, l_mfl);
        ge.d_players.add(new Player("Shashi",ge));
        ge.setCurrentState(new Startup(ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        OrderExecution oe = new OrderExecution(ge);
        cmd.execute(ge);
        assertTrue(oe.isWinner());
>>>>>>> Stashed changes
    }

    /**
     * Tests for the Winner method in the OrderExecution class.
     * Checks if the method correctly identifies no winner when multiple players exist.
     *
     * @throws CountryDoesNotExistException    If the country does not exist.
     * @throws ContinentAlreadyExistsException If the continent already exists.
     * @throws ContinentDoesNotExistException  If the continent does not exist.
     * @throws DuplicateCountryException       If a duplicate country is encountered.
     * @throws FileNotFoundException           If the specified file is not found.
     */
    @Test
    public void isWinnerTest2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
<<<<<<< Updated upstream
        MapInterface l_mp = new MapInterface();
        GameEngine l_ge = new GameEngine();
        l_ge.d_worldmap = l_mp.loadMap(l_ge, "usa9.map");
        l_ge.d_players.add(new Player("Shashi", l_ge));
        l_ge.d_players.add(new Player("Priyanshu", l_ge));
        l_ge.setCurrentState(new Startup(l_ge));
        StartupCommands l_cmd = new StartupCommands("assigncountries");
        OrderExecution l_oe = new OrderExecution(l_ge);
        l_cmd.execute(l_ge);
        assertFalse(l_oe.isWinner());
=======
        MapInterface mp = null;
        GameEngine ge = new GameEngine();
        MapFileLoader l_mfl = new MapFileLoader(ge, "usa9.map");

        if(l_mfl.isConquest()){
            mp = new MapAdapter(new ConquestMapInterface());
        }else{
            mp = new MapInterface();
        }
        ge.d_worldmap = mp.loadMap(ge, l_mfl);
        ge.d_players.add(new Player("Shashi",ge));
        ge.d_players.add(new Player("Priyanshu",ge));
        ge.setCurrentState(new Startup(ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        OrderExecution oe = new OrderExecution(ge);
        cmd.execute(ge);
        assertFalse(oe.isWinner());
>>>>>>> Stashed changes
    }
}