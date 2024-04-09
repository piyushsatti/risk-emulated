package controller.middleware.commands;

import controller.GameEngine;
import controller.middleware.commands.StartupCommands;
import controller.statepattern.MapEditor;
import controller.statepattern.gameplay.Startup;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import models.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * The StartupCommandsTest class contains unit tests for the StartupCommands class.
 */
public class StartupCommandsTest {
    /**
     * Represents the game engine responsible for managing game logic and state.
     */
    GameEngine d_gameEngine;

    /**
     * This method is executed before each test method to set up the testing environment.
     */
    @Before
    public void beforeTest() {
        d_gameEngine = new GameEngine();
        d_gameEngine.setCurrentState(new Startup((d_gameEngine)));
    }

    /**
     * Test case for validating the loadmap command with no parameters.
     */
    @Test
    public void testLoadMap1() {
        String l_cmd = "loadmap ";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for validating the loadmap command with no filename.
     */
    @Test
    public void testLoadMap2() {
        String l_cmd = "loadmap piyush";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for validating the loadmap command with a valid filename.
     */
    @Test
    public void testLoadMap3() {
        String l_cmd = "loadmap piyush.map";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for validating the loadmap command with multiple filenames.
     */
    @Test
    public void testLoadMap4() {
        String l_cmd = "loadmap piyush satti.map";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test cases for validating the gameplayer command.
     */
    @Test
    public void testGamePlayer1() {
        String l_cmd = "gameplayer ";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test cases for validating the gameplayer command with incorrect parameters
     */
    @Test
    public void testGamePlayer2() {
        String l_cmd = "gameplayer Shashi Piyush";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test cases for validating the gameplayer command to add and remove player
     */
    @Test
    public void testGamePlayer3() {
        String l_cmd = "gameplayer -add Shashi -remove Piyush";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test cases for validating the gameplayer command to add player
     */
    @Test
    public void testGamePlayer4() {
        String l_cmd = "gameplayer -add Shashi";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test cases for validating the gameplayer command to remove player
     */
    @Test
    public void testGamePlayer5() {
        String l_cmd = "gameplayer -remove Shashi";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test cases for validating the gameplayer command with incorrect command
     */
    @Test
    public void testGamePlayer6() {
        String l_cmd = "gameplayer -add -remove Shashi ";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test cases for validating the gameplayer command with incorrect command
     */
    @Test
    public void testGamePlayer7() {
        String l_cmd = "gameplayer -add Shashi -remove ";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test cases for validating the gameplayer command with incorrect command
     */
    @Test
    public void testGamePlayer8() {
        String l_cmd = "gameplayer -add Shashi -remove ";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test cases for validating the gameplayer command with incorrect command
     */
    @Test
    public void testGamePlayer9() {
        String l_cmd = "gameplayer -add Shashi Piyush Devdutt -remove Priyanshu";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test cases for validating the gameplayer command with incorrect command
     */
    @Test
    public void testGamePlayer10() {
        String l_cmd = "gameplayer -add Shashi -remove Priyanshu Piyush Devdutt";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test cases for validating the gameplayer command to add and remove multiple players
     */
    @Test
    public void testGamePlayer11() {
        String l_cmd = "gameplayer -add Shashi -remove Priyanshu -remove Piyush -add Devdutt ";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test cases for validating the assignCountries command with incorrect command
     */
    @Test
    public void testAssignCountries1() {
        String l_cmd = "assigncountries India ";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test cases for validating the assignCountries command with incorrect command
     */
    @Test
    public void testAssignCountries2() {
        String l_cmd = "assigncountries India Nepal ";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test cases for validating the assignCountries command with no parameters
     */
    @Test
    public void testAssignCountries3() {
        String l_cmd = "assigncountries ";
        StartupCommands l_obj = new StartupCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

}