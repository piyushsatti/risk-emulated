package controller.middleware.commands;

import controller.GameEngine;
import controller.middleware.commands.TournamentCommands;
import controller.statepattern.MapEditor;
import controller.statepattern.Tournament;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TournamentTest {
    /**
     * Represents the game engine responsible for managing game logic and state.
     */
    GameEngine d_gameEngine;
    /**
     * This method is executed before each test method to set up the testing environment.
     */
    @Before
    public void beforeTest() throws CountryDoesNotExistException, InvalidCommandException {
        d_gameEngine = new GameEngine();
        d_gameEngine.setCurrentState(new Tournament(d_gameEngine));
    }

    /**
     * Test cases for validating the tournament command with incorrect command
     */
    @Test
    public void testTournament1()
    {
        String cmd = "tournament -M devdutt usa1.map usa2.map pikachu3.map pikachu4.map -P cheater human -G 1 -D 10";
        TournamentCommands obj = new TournamentCommands(cmd);
        assertFalse(obj.validateCommand(d_gameEngine));
    }
    /**
     * Test cases for validating the tournament command with correct command
     */
    @Test
    public void testTournament2()
    {
        String cmd = "tournament -M devdutt.map usa1.map usa2.map pikachu3.map pikachu4.map -P cheater human -G 1 -D 10";
        TournamentCommands obj = new TournamentCommands(cmd);
        assertTrue(obj.validateCommand(d_gameEngine));
    }
    /**
     * Test cases for validating the tournament command with correct command
     */
    @Test
    public void testTournament3()
    {
        String cmd = "tournament -M devdutt.map usa1.map usa2.map pikachu3.map -P cheater human -G 1 -D 10";
        TournamentCommands obj = new TournamentCommands(cmd);
        assertTrue(obj.validateCommand(d_gameEngine));
    }
    /**
     * Test cases for validating the tournament command with correct command
     */
    @Test
    public void testTournament4()
    {
        String cmd = "tournament -M devdutt.map usa1.map usa2.map  -P cheater human -G 1 -D 10";
        TournamentCommands obj = new TournamentCommands(cmd);
        assertTrue(obj.validateCommand(d_gameEngine));
    }
    /**
     * Test cases for validating the tournament command with correct command
     */
    @Test
    public void testTournament6()
    {
        String cmd = "tournament -M devdutt.map   -P cheater human -G 1 -D 10";
        TournamentCommands obj = new TournamentCommands(cmd);
        assertTrue(obj.validateCommand(d_gameEngine));
    }
    /**
     * Test cases for validating the tournament command with correct command
     */
    @Test
    public void testTournament7()
    {
        String cmd = "tournament -M devdutt.map priyanshu.map   -P cheater human -G 1 -D 10";
        TournamentCommands obj = new TournamentCommands(cmd);
        assertTrue(obj.validateCommand(d_gameEngine));
    }
    /**
     * Test cases for validating the tournament command with incorrect command
     */
    @Test
    public void testTournament8()
    {
        String cmd = "tournament -M devdutt.map priyanshu shashi.map   -P cheater human -G 1 -D 10";
        TournamentCommands obj = new TournamentCommands(cmd);
        assertFalse(obj.validateCommand(d_gameEngine));
    }
    /**
     * Test cases for validating the tournament command with incorrect command
     */
    @Test
    public void testTournament9()
    {
        String cmd = "tournament -M devdutt.map priyanshu.map shashi.map   -P cheater human -G 1 -D 100";
        TournamentCommands obj = new TournamentCommands(cmd);
        assertFalse(obj.validateCommand(d_gameEngine));
    }
    /**
     * Test cases for validating the tournament command with incorrect command
     */
    @Test
    public void testTournament10()
    {
        String cmd = "tournament -M devdutt.map priyanshu.map shashi.map   -P  human -G 1 -D 12";
        TournamentCommands obj = new TournamentCommands(cmd);
        assertFalse(obj.validateCommand(d_gameEngine));
    }
    /**
     * Test cases for validating the tournament command with correct command
     */
    @Test
    public void testTournament11()
    {
        String cmd = "tournament -M devdutt.map priyanshu.map shashi.map   -P  human cheater random aggressive -G 1 -D 12";
        TournamentCommands obj = new TournamentCommands(cmd);
        assertTrue(obj.validateCommand(d_gameEngine));
    }
    /**
     * Test cases for validating the tournament command with incorrect command
     */
    @Test
    public void testTournament12()
    {
        String cmd = "tournament -M devdutt.map priyanshu.map shashi.map   -P  human cheater random aggressive benevolent -G 1 -D 12";
        TournamentCommands obj = new TournamentCommands(cmd);
        assertFalse(obj.validateCommand(d_gameEngine));
    }
    /**
     * Test cases for validating the tournament command with correct command
     */
    @Test
    public void testTournament13()
    {
        String cmd = "tournament -M devdutt.map priyanshu.map shashi.map   -P  human cheater random aggressive -G 5 -D 12";
        TournamentCommands obj = new TournamentCommands(cmd);
        assertTrue(obj.validateCommand(d_gameEngine));
    }
    /**
     * Test cases for validating the tournament command with incorrect command
     */
    @Test
    public void testTournament14()
    {
        String cmd = "tournament -M devdutt.map priyanshu.map shashi.map   -P  human cheater random aggressive -G 6 -D 12";
        TournamentCommands obj = new TournamentCommands(cmd);
        assertFalse(obj.validateCommand(d_gameEngine));
    }
    /**
     * Test cases for validating the tournament command with correct command
     */
    @Test
    public void testTournament15()
    {
        String cmd = "tournament -M devdutt.map priyanshu.map shashi.map   -P  human cheater random aggressive -G  4 -D 12";
        TournamentCommands obj = new TournamentCommands(cmd);
        assertTrue(obj.validateCommand(d_gameEngine));
    }
}