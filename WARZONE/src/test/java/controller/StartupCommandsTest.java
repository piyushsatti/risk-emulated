package controller;

import controller.middleware.commands.StartupCommands;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The StartupCommandsTest class contains unit tests for the StartupCommands class.
 */
public class StartupCommandsTest {
    /**
     * Test case for validating the loadmap command with no parameters.
     */
    @Test
    public void testLoadMap1()
    {
        String cmd = "loadmap ";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    /**
     * Test case for validating the loadmap command with no filename.
     */
    @Test
    public void testLoadMap2()
    {
        String cmd = "loadmap piyush";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    /**
     * Test case for validating the loadmap command with a valid filename.
     */
    @Test
    public void testLoadMap3()
    {
        String cmd = "loadmap piyush.map";
        StartupCommands obj = new StartupCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    /**
     * Test case for validating the loadmap command with multiple filenames.
     */
    @Test
    public void testLoadMap4()
    {
        String cmd = "loadmap piyush satti.map";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    /**
     * Test cases for validating the gameplayer command.
     */
    @Test
    public void testGamePlayer1()
    {
        String cmd = "gameplayer ";
        StartupCommands obj = new StartupCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    /**
     * Test cases for validating the gameplayer command with incorrect parameters
     */
    @Test
    public void testGamePlayer2()
    {
        String cmd = "gameplayer Shashi Piyush";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    /**
     * Test cases for validating the gameplayer command to add and remove player
     */
    @Test
    public void testGamePlayer3()
    {
        String cmd = "gameplayer -add Shashi -remove Piyush";
        StartupCommands obj = new StartupCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    /**
     * Test cases for validating the gameplayer command to add player
     */
    @Test
    public void testGamePlayer4()
    {
        String cmd = "gameplayer -add Shashi";
        StartupCommands obj = new StartupCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    /**
     * Test cases for validating the gameplayer command to remove player
     */
    @Test
    public void testGamePlayer5()
    {
        String cmd = "gameplayer -remove Shashi";
        StartupCommands obj = new StartupCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    /**
     * Test cases for validating the gameplayer command with incorrect command
     */
    @Test
    public void testGamePlayer6()
    {
        String cmd = "gameplayer -add -remove Shashi ";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    /**
     * Test cases for validating the gameplayer command with incorrect command
     */
    @Test
    public void testGamePlayer7()
    {
        String cmd = "gameplayer -add Shashi -remove ";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    /**
     * Test cases for validating the gameplayer command with incorrect command
     */
    @Test
    public void testGamePlayer8()
    {
        String cmd ="gameplayer -add Shashi -remove ";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    /**
     * Test cases for validating the gameplayer command with incorrect command
     */
    @Test
    public void testGamePlayer9()
    {
        String cmd ="gameplayer -add Shashi Piyush Devdutt -remove Priyanshu";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    /**
     * Test cases for validating the gameplayer command with incorrect command
     */
    @Test
    public void testGamePlayer10()
    {
        String cmd ="gameplayer -add Shashi -remove Priyanshu Piyush Devdutt";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    /**
     * Test cases for validating the gameplayer command to add and remove multiple players
    */
    @Test
    public void testGamePlayer11()
    {
        String cmd ="gameplayer -add Shashi -remove Priyanshu -remove Piyush -add Devdutt ";
        StartupCommands obj = new StartupCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    /**
     * Test cases for validating the assignCountries command with incorrect command
     */
    @Test
    public void testAssignCountries1()
    {
        String cmd = "assigncountries India ";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    /**
     * Test cases for validating the assignCountries command with incorrect command
     */
    @Test
    public void testAssignCountries2()
    {
        String cmd = "assigncountries India Nepal ";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    /**
     * Test cases for validating the assignCountries command with no parameters
     */
    @Test
    public void testAssignCountries3()
    {
        String cmd = "assigncountries ";
        StartupCommands obj = new StartupCommands(cmd);
        assertTrue(obj.validateCommand());
    }
}