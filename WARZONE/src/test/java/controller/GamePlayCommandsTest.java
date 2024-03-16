package controller;

import controller.middleware.commands.MapEditorCommands;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GamePlayCommandsTest {
    @Test
    public void testLoadMap1()
    {
        String cmd = "loadmap ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testLoadMap2()
    {
        String cmd = "loadmap piyush";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testLoadMap3()
    {
        String cmd = "loadmap piyush.map";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testLoadMap4()
    {
        String cmd = "loadmap piyush satti.map";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testGamePlayer1()
    {
        String cmd = "gameplayer ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer2()
    {
        String cmd = "gameplayer Shashi Piyush";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer3()
    {
        String cmd = "gameplayer -add Shashi -remove Piyush";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testGamePlayer4()
    {
        String cmd = "gameplayer -add Shashi";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testGamePlayer5()
    {
        String cmd = "gameplayer -remove Shashi";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }
    @Test
    public void testGamePlayer6()
    {
        String cmd = "gameplayer -add -remove Shashi ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer7()
    {
        String cmd = "gameplayer -add Shashi -remove ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer8()
    {
        String cmd ="gameplayer -add Shashi -remove ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer9()
    {
        String cmd ="gameplayer -add Shashi Piyush Devdutt -remove Priyanshu";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer10()
    {
        String cmd ="gameplayer -add Shashi -remove Priyanshu Piyush Devdutt";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testGamePlayer11()
    {
        String cmd ="gameplayer -add Shashi -remove Priyanshu -remove Piyush -add Devdutt";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testAssignCountries1()
    {
        String cmd = "assigncountries India ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testAssignCountries2()
    {
        String cmd = "assigncountries India Nepal ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }
    @Test
    public void testAssignCountries3()
    {
        String cmd = "assigncountries ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }
}