package controller;

import controller.middleware.commands.StartupCommands;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StartupCommandsTest {
    @Test
    public void testLoadMap1()
    {
        String cmd = "loadmap ";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testLoadMap2()
    {
        String cmd = "loadmap piyush";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testLoadMap3()
    {
        String cmd = "loadmap piyush.map";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testLoadMap4()
    {
        String cmd = "loadmap piyush satti.map";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testGamePlayer1()
    {
        String cmd = "gameplayer ";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testGamePlayer2()
    {
        String cmd = "gameplayer Shashi Piyush";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer3()
    {
        String cmd = "gameplayer -add Shashi -remove Piyush";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testGamePlayer4()
    {
        String cmd = "gameplayer -add Shashi";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testGamePlayer5()
    {
        String cmd = "gameplayer -remove Shashi";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testGamePlayer6()
    {
        String cmd = "gameplayer -add -remove Shashi ";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer7()
    {
        String cmd = "gameplayer -add Shashi -remove ";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer8()
    {
        String cmd ="gameplayer -add Shashi -remove ";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer9()
    {
        String cmd ="gameplayer -add Shashi Piyush Devdutt -remove Priyanshu";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer10()
    {
        String cmd ="gameplayer -add Shashi -remove Priyanshu Piyush Devdutt";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer11()
    {
        String cmd ="gameplayer -add Shashi -remove Priyanshu -remove Piyush -add Devdutt ";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testAssignCountries1()
    {
        String cmd = "assigncountries India ";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testAssignCountries2()
    {
        String cmd = "assigncountries India Nepal ";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testAssignCountries3()
    {
        String cmd = "assigncountries ";
        StartupCommandsTest obj = new StartupCommandsTest(cmd);
        assertTrue(obj.validateCommand());
    }
}