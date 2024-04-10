package controller.middleware.commands;

import controller.GameEngine;
import controller.statepattern.Phase;
import controller.statepattern.gameplay.IssueOrder;
import models.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The IssueOrderCommandsTest class contains unit tests for the validation of various order commands.
 * It tests different scenarios for the validation of Bomb, Blockade, Negotiate, Deploy, Advance, and Airlift commands.
 */
public class IssueOrderCommandsTest {
    /**
     * Represents an instance of the game engine.
     */
    GameEngine d_ge = new GameEngine();
    /**
     * current phase
     */
    Phase d_phase = new IssueOrder(d_ge);



    /**
     * Represents an instance of a player named "Devdutt" associated with the game engine.
     */
    Player d_player = new Player("Devdutt", d_ge);

    /**
     * test to validate invalid command for the given game phase
     */
    @Test
    public void testInvalidCommand() {
        String l_cmd = "tournament -M usa9.map test_map.map -P benevolent aggressive -G 2 -D 10 ";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    @Before
    public void setUp()
    {
        d_ge.setCurrentState(d_phase);
    }

    /**
     * Tests the validation of the Bomb command with no parameters.
     */
    @Test
    public void testBomb1() {
        String l_cmd = "bomb ";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Bomb command with no parameters.
     */
    @Test
    public void testValidBomb1() {
        String l_cmd = "bomb PandaLand";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertTrue(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Bomb command with one parameter.
     */
    @Test
    public void testBomb2() {
        String l_cmd = "bomb piyush";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertTrue(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Bomb command with two parameters.
     */
    @Test
    public void testBomb3() {
        String l_cmd = "bomb piyush satti";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Blockade command with no parameters.
     */
    @Test
    public void testBlockade1() {
        String l_cmd = "blockade ";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Blockade command with one parameter.
     */
    @Test
    public void testBlockade2() {
        String l_cmd = "blockade Piyush";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertTrue(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Blockade command with two parameters.
     */
    @Test
    public void testBlockade3() {
        String l_cmd = "blockade piyush satti";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Negotiate command with no parameters.
     */
    @Test
    public void testNegotiate1() {
        String l_cmd = "negotiate ";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Negotiate command with one parameter.
     */
    @Test
    public void testNegotiate2() {
        String l_cmd = "negotiate piyush";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertTrue(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Negotiate command with two parameters.
     */
    @Test
    public void testNegotiate3() {
        String l_cmd = "negotiate piyush satti";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Deploy command with incorrect parameters.
     */
    @Test
    public void testDeploy1() {
        String l_cmd = "deploy India Shashi";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Deploy command with one parameter.
     */
    @Test
    public void testDeploy2() {
        String l_cmd = "deploy India ";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Deploy command with two parameters.
     */
    @Test
    public void testDeploy3() {
        String l_cmd = "deploy India 300";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertTrue(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Deploy command with no parameters.
     */
    @Test
    public void testDeploy4() {
        String l_cmd = "deploy ";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Advance command with no parameters.
     */
    @Test
    public void testAdvance1() {
        String l_cmd = "advance ";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Advance command with incorrect parameters.
     */
    @Test
    public void testAdvance2() {
        String l_cmd = "advance India Bangladesh";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Advance command with incorrect parameters.
     */
    @Test
    public void testAdvance3() {
        String l_cmd = "advance India Bangladesh 300";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertTrue(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Advance command with incorrect parameters.
     */
    @Test
    public void testAdvance4() {
        String l_cmd = "advance India 300 ";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Advance command with incorrect parameters.
     */
    @Test
    public void testAdvance5() {
        String l_cmd = "advance 300 ";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Advance command with incorrect parameters.
     */
    @Test
    public void testAdvance6() {
        String l_cmd = "advance India Bangladesh Nepal";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Airlift command with no parameters.
     */
    @Test
    public void testAirlift1() {
        String l_cmd = "airlift ";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Airlift command with incorrect parameters.
     */
    @Test
    public void testAirlift2() {
        String l_cmd = "airlift India Bangladesh";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Airlift command with incorrect parameters.
     */
    @Test
    public void testAirlift3() {
        String l_cmd = "airlift India Bangladesh 300";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertTrue(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Airlift command with incorrect parameters.
     */
    @Test
    public void testAirlift4() {
        String l_cmd = "airlift India 300 ";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Airlift command with incorrect parameters.
     */
    @Test
    public void testAirlift5() {
        String l_cmd = "airlift 300 ";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the Airlift command with incorrect parameters.
     */
    @Test
    public void testAirlift6() {
        String l_cmd = "airlift India Bangladesh Nepal";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the valid savegame command
     */
    @Test
    public void testSaveGame1() {
        String l_cmd = "savegame     devdutt";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertTrue(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the invalid savegame command
     */
    @Test
    public void testSaveGame2() {
        String l_cmd = "savegame     ";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the invalid savegame command
     */
    @Test
    public void testSaveGame3() {
        String l_cmd = "savegame    shashi pikachu ";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the valid loadgame command
     */
    @Test
    public void loadGame1() {
        String l_cmd = "loadgame     devdutt";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertTrue(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the invalid loadgame command
     */
    @Test
    public void loadGame2() {
        String l_cmd = "loadgame     ";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }

    /**
     * Tests the validation of the invalid loadgame command
     */
    @Test
    public void loadGame3() {
        String l_cmd = "loadgame    shashi pikachu ";
        IssueOrderCommands l_obj = new IssueOrderCommands(l_cmd, d_player);
        assertFalse(l_obj.validateCommand(d_ge));
    }
}
