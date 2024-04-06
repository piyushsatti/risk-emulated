package controller.middleware.commands;

import controller.GameEngine;
import controller.middleware.commands.IssueOrderCommands;
import models.Player;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * The IssueOrderCommandsTest class contains unit tests for the validation of various order commands.
 * It tests different scenarios for the validation of Bomb, Blockade, Negotiate, Deploy, Advance, and Airlift commands.
 */
public class IssueOrderCommandsTest {
    /**
     * Represents an instance of the game engine.
     */
    GameEngine ge = new GameEngine();

    /**
     * Represents an instance of a player named "Devdutt" associated with the game engine.
     */
    Player player = new Player("Devdutt", ge);


    /**
     * Tests the validation of the Bomb command with no parameters.
     */
    @Test
    public void testBomb1() {
        String cmd = "bomb ";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Bomb command with one parameter.
     */
    @Test
    public void testBomb2() {
        String cmd = "bomb piyush";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Bomb command with two parameters.
     */
    @Test
    public void testBomb3() {
        String cmd = "bomb piyush satti";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Blockade command with no parameters.
     */
    @Test
    public void testBlockade1() {
        String cmd = "blockade ";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Blockade command with one parameter.
     */
    @Test
    public void testBlockade2() {
        String cmd = "blockade piyush";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Blockade command with two parameters.
     */
    @Test
    public void testBlockade3() {
        String cmd = "blockade piyush satti";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Negotiate command with no parameters.
     */
    @Test
    public void testNegotiate1() {
        String cmd = "negotiate ";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Negotiate command with one parameter.
     */
    @Test
    public void testNegotiate2() {
        String cmd = "negotiate piyush";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Negotiate command with two parameters.
     */
    @Test
    public void testNegotiate3() {
        String cmd = "negotiate piyush satti";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Deploy command with incorrect parameters.
     */
    @Test
    public void testDeploy1() {
        String cmd = "deploy India Shashi";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Deploy command with one parameter.
     */
    @Test
    public void testDeploy2() {
        String cmd = "deploy India ";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Deploy command with two parameters.
     */
    @Test
    public void testDeploy3() {
        String cmd = "deploy India 300";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Deploy command with no parameters.
     */
    @Test
    public void testDeploy4() {
        String cmd = "deploy ";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Advance command with no parameters.
     */
    @Test
    public void testAdvance1() {
        String cmd = "advance ";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Advance command with incorrect parameters.
     */
    @Test
    public void testAdvance2() {
        String cmd = "advance India Bangladesh";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Advance command with incorrect parameters.
     */
    @Test
    public void testAdvance3() {
        String cmd = "advance India Bangladesh 300";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Advance command with incorrect parameters.
     */
    @Test
    public void testAdvance4() {
        String cmd = "advance India 300 ";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Advance command with incorrect parameters.
     */
    @Test
    public void testAdvance5() {
        String cmd = "advance 300 ";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Advance command with incorrect parameters.
     */
    @Test
    public void testAdvance6() {
        String cmd = "advance India Bangladesh Nepal";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Airlift command with no parameters.
     */
    @Test
    public void testAirlift1() {
        String cmd = "airlift ";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Airlift command with incorrect parameters.
     */
    @Test
    public void testAirlift2() {
        String cmd = "airlift India Bangladesh";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Airlift command with incorrect parameters.
     */
    @Test
    public void testAirlift3() {
        String cmd = "airlift India Bangladesh 300";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Airlift command with incorrect parameters.
     */
    @Test
    public void testAirlift4() {
        String cmd = "airlift India 300 ";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Airlift command with incorrect parameters.
     */
    @Test
    public void testAirlift5() {
        String cmd = "airlift 300 ";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }

    /**
     * Tests the validation of the Airlift command with incorrect parameters.
     */
    @Test
    public void testAirlift6() {
        String cmd = "airlift India Bangladesh Nepal";
        IssueOrderCommands obj = new IssueOrderCommands(cmd, player);
        assertFalse(obj.validateCommand(ge));
    }
}
