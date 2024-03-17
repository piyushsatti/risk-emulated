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
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testLoadMap2()
    {
        String cmd = "loadmap piyush";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testLoadMap3()
    {
        String cmd = "loadmap piyush.map";
        StartupCommands obj = new StartupCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testLoadMap4()
    {
        String cmd = "loadmap piyush satti.map";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testGamePlayer1()
    {
        String cmd = "gameplayer ";
        StartupCommands obj = new StartupCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testGamePlayer2()
    {
        String cmd = "gameplayer Shashi Piyush";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer3()
    {
        String cmd = "gameplayer -add Shashi -remove Piyush";
        StartupCommands obj = new StartupCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testGamePlayer4()
    {
        String cmd = "gameplayer -add Shashi";
        StartupCommands obj = new StartupCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testGamePlayer5()
    {
        String cmd = "gameplayer -remove Shashi";
        StartupCommands obj = new StartupCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testGamePlayer6()
    {
        String cmd = "gameplayer -add -remove Shashi ";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer7()
    {
        String cmd = "gameplayer -add Shashi -remove ";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer8()
    {
        String cmd ="gameplayer -add Shashi -remove ";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer9()
    {
        String cmd ="gameplayer -add Shashi Piyush Devdutt -remove Priyanshu";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer10()
    {
        String cmd ="gameplayer -add Shashi -remove Priyanshu Piyush Devdutt";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer11()
    {
        String cmd ="gameplayer -add Shashi -remove Priyanshu -remove Piyush -add Devdutt ";
        StartupCommands obj = new StartupCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testAssignCountries1()
    {
        String cmd = "assigncountries India ";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testAssignCountries2()
    {
        String cmd = "assigncountries India Nepal ";
        StartupCommands obj = new StartupCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testAssignCountries3()
    {
        String cmd = "assigncountries ";
        StartupCommands obj = new StartupCommands(cmd);
        assertTrue(obj.validateCommand());
    }
}