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
     */
    @Test
    public void isWinnerTest1() {
        MapInterface l_mp = null;
        GameEngine l_ge = new GameEngine();
        MapFileLoader l_mfl = new MapFileLoader(l_ge, "usa9.map");

        if (l_mfl.isConquest()) {
            l_mp = new MapAdapter(new ConquestMapInterface());
        } else {
            l_mp = new MapInterface();
        }
        l_ge.d_worldmap = l_mp.loadMap(l_ge, l_mfl);
        l_ge.d_players.add(new Player("Shashi", l_ge));
        l_ge.setCurrentState(new Startup(l_ge));
        StartupCommands l_cmd = new StartupCommands("assigncountries");
        OrderExecution l_oe = new OrderExecution(l_ge);
        l_cmd.execute(l_ge);
        assertTrue(l_oe.isWinner());
    }

    /**
     * Tests for the Winner method in the OrderExecution class.
     * Checks if the method correctly identifies no winner when multiple players exist.
     */
    @Test
    public void isWinnerTest2() {
        MapInterface l_mp = null;
        GameEngine l_ge = new GameEngine();
        MapFileLoader l_mfl = new MapFileLoader(l_ge, "usa9.map");

        if (l_mfl.isConquest()) {
            l_mp = new MapAdapter(new ConquestMapInterface());
        } else {
            l_mp = new MapInterface();
        }
        l_ge.d_worldmap = l_mp.loadMap(l_ge, l_mfl);
        l_ge.d_players.add(new Player("Shashi", l_ge));
        l_ge.d_players.add(new Player("Priyanshu", l_ge));
        l_ge.setCurrentState(new Startup(l_ge));
        StartupCommands l_cmd = new StartupCommands("assigncountries");
        OrderExecution l_oe = new OrderExecution(l_ge);
        l_cmd.execute(l_ge);
        assertFalse(l_oe.isWinner());
    }
}