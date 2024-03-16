package controller;

import controller.middleware.commands.GamePlayCommands;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GamePlayCommandsTest {
    @Test
    public void testLoadMap1()
    {
        String cmd = "loadmap ";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testLoadMap2()
    {
        String cmd = "loadmap piyush";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testLoadMap3()
    {
        String cmd = "loadmap piyush.map";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testLoadMap4()
    {
        String cmd = "loadmap piyush satti.map";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testGamePlayer1()
    {
        String cmd = "gameplayer ";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testGamePlayer2()
    {
        String cmd = "gameplayer Shashi Piyush";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer3()
    {
        String cmd = "gameplayer -add Shashi -remove Piyush";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testGamePlayer4()
    {
        String cmd = "gameplayer -add Shashi";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testGamePlayer5()
    {
        String cmd = "gameplayer -remove Shashi";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testGamePlayer6()
    {
        String cmd = "gameplayer -add -remove Shashi ";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer7()
    {
        String cmd = "gameplayer -add Shashi -remove ";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer8()
    {
        String cmd ="gameplayer -add Shashi -remove ";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer9()
    {
        String cmd ="gameplayer -add Shashi Piyush Devdutt -remove Priyanshu";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer10()
    {
        String cmd ="gameplayer -add Shashi -remove Priyanshu Piyush Devdutt";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer11()
    {
        String cmd ="gameplayer -add Shashi -remove Priyanshu -remove Piyush -add Devdutt ";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testAssignCountries1()
    {
        String cmd = "assigncountries India ";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testAssignCountries2()
    {
        String cmd = "assigncountries India Nepal ";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testAssignCountries3()
    {
        String cmd = "assigncountries ";
        GamePlayCommands obj = new GamePlayCommands(cmd);
        assertTrue(obj.validateCommand());
    }
}